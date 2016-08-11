package eu.cyfronoid.core.log4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

public class ZippedDailyRollingFileAppender extends FileAppender {
    private static final TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");

    // The code assumes that the following constants are in a increasing sequence.
    private static final int TOP_OF_TROUBLE = -1;
    private static final int TOP_OF_MINUTE = 0;
    private static final int TOP_OF_HOUR = 1;
    private static final int HALF_DAY = 2;
    private static final int TOP_OF_DAY = 3;
    private static final int TOP_OF_WEEK = 4;
    private static final int TOP_OF_MONTH = 5;

    private String datePattern = "'.'yyyy-MM-dd";
    private boolean compressBackups = false;
    private int maxDays = 7;

    private String scheduledFilename;

    private long nextCheck = System.currentTimeMillis() - 1;
    Date now = new Date();
    SimpleDateFormat datePatternFormat = new SimpleDateFormat(datePattern);
    RollingCalendar rollingCalendar = new RollingCalendar();
    int checkPeriod = TOP_OF_TROUBLE;

    public ZippedDailyRollingFileAppender() {
    }

    public ZippedDailyRollingFileAppender(Layout layout, String filename, String datePattern) throws IOException {
        super(layout, filename, true);
        this.datePattern = datePattern;
        activateOptions();
    }

    public void setDatePattern(String pattern) {
        datePattern = pattern;
    }

    public String getDatePattern() {
        return datePattern;
    }

    public void activateOptions() {
        super.activateOptions();

        if ((datePattern != null) && (fileName != null)) {
            now.setTime(System.currentTimeMillis());
            datePatternFormat = new SimpleDateFormat(datePattern);
            int type = computeCheckPeriod();
            printPeriodicity(type);
            rollingCalendar.setType(type);
            File file = new File(fileName);
            scheduledFilename = fileName + datePatternFormat.format(new Date(file.lastModified()));
        } else {
            LogLog.error("Either File or DatePattern options are not set for appender [" + name + "].");
        }
    }

    void printPeriodicity(int type) {
        switch (type) {
        case TOP_OF_MINUTE:
            LogLog.debug("Appender [" + name + "] to be rolled every minute.");

            break;

        case TOP_OF_HOUR:
            LogLog.debug("Appender [" + name +
                "] to be rolled on top of every hour.");

            break;

        case HALF_DAY:
            LogLog.debug("Appender [" + name +
                "] to be rolled at midday and midnight.");

            break;

        case TOP_OF_DAY:
            LogLog.debug("Appender [" + name + "] to be rolled at midnight.");

            break;

        case TOP_OF_WEEK:
            LogLog.debug("Appender [" + name +
                "] to be rolled at start of week.");

            break;

        case TOP_OF_MONTH:
            LogLog.debug("Appender [" + name +
                "] to be rolled at start of every month.");

            break;

        default:
            LogLog.warn("Unknown periodicity for appender [" + name + "].");
        }
    }

    int computeCheckPeriod() {
        RollingCalendar rollingCalendar = new RollingCalendar(gmtTimeZone, Locale.ENGLISH);

        // set sate to 1970-01-01 00:00:00 GMT
        Date epoch = new Date(0);

        if (datePattern != null) {
            for (int i = TOP_OF_MINUTE; i <= TOP_OF_MONTH; i++) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
                simpleDateFormat.setTimeZone(gmtTimeZone); // do all date formatting in GMT

                String r0 = simpleDateFormat.format(epoch);
                rollingCalendar.setType(i);

                Date next = new Date(rollingCalendar.getNextCheckMillis(epoch));
                String r1 = simpleDateFormat.format(next);
                //System.out.println("Type = "+i+", r0 = "+r0+", r1 = "+r1);
                if ((r0 != null) && (r1 != null) && !r0.equals(r1)) {
                    return i;
                }
            }
        }

        return TOP_OF_TROUBLE; // Deliberately head for trouble...
    }

    void rollOver() throws IOException {
        /* Compute filename, but only if datePattern is specified */
        if (datePattern == null) {
            errorHandler.error("Missing DatePattern option in rollOver().");
            return;
        }

        String datedFilename = fileName + datePatternFormat.format(now);

        // It is too early to roll over because we are still within the
        // bounds of the current interval. Rollover will occur once the
        // next interval is reached.
        if (scheduledFilename.equals(datedFilename)) {
            return;
        }

        // close current file, and rename it to datedFilename
        this.closeFile();

        File target = new File(scheduledFilename);

        if (target.exists()) {
            target.delete();
        }

        File file = new File(fileName);
        boolean result = file.renameTo(target);

        if (result) {
            LogLog.debug(fileName + " -> " + scheduledFilename);
        } else {
            LogLog.error("Failed to rename [" + fileName + "] to [" + scheduledFilename + "].");
        }

        try {
            // This will also close the file. This is OK since multiple
            // close operations are safe.
            this.setFile(fileName, false, this.bufferedIO, this.bufferSize);
        } catch (IOException e) {
            errorHandler.error("setFile(" + fileName + ", false) call failed.");
        }

        scheduledFilename = datedFilename;
    }

    protected void subAppend(LoggingEvent event) {
        long n = System.currentTimeMillis();

        if (n >= nextCheck) {
            now.setTime(n);
            nextCheck = rollingCalendar.getNextCheckMillis(now);

            try {
                cleanupAndRollOver();
            } catch (IOException ioe) {
                LogLog.error("rollOver() failed.", ioe);
            }
        }

        super.subAppend(event);
    }

    //Added
    protected void cleanupAndRollOver() throws IOException {
        File file = new File(fileName);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -getMaxDays());

        Date cutoffDate = cal.getTime();

        if (file.getParentFile().exists()) {
            File[] files = file.getParentFile().listFiles(p -> !p.isDirectory() && p.getName().toUpperCase().startsWith(file.getName().toUpperCase()));
            int nameLength = file.getName().length();

            for (int i = 0; i < files.length; i++) {
                String datePart = null;

                try {
                    datePart = files[i].getName().substring(nameLength);

                    Date date = datePatternFormat.parse(datePart);

                    if (date.before(cutoffDate)) {
                        files[i].delete();
                    } else if (shouldCompress()) {
                        zipAndDelete(files[i]);
                    }
                } catch (Exception pe) {

                }
            }
        }

        rollOver();
    }

    private void zipAndDelete(File file) throws IOException {
        if (!file.getName().endsWith(".zip")) {
            File zipFile = new File(file.getParent(), file.getName() + ".zip");
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);
            ZipEntry zipEntry = new ZipEntry(file.getName());
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[4096];

            while (true) {
                int bytesRead = fis.read(buffer);

                if (bytesRead == -1) {
                    break;
                } else {
                    zos.write(buffer, 0, bytesRead);
                }
            }

            zos.closeEntry();
            fis.close();
            zos.close();
            file.delete();
        }
    }

    public String getMaxNumberOfDays() {
        return String.valueOf(maxDays);
    }

    public int getMaxDays() {
        return maxDays;
    }

    public void setMaxNumberOfDays(String maxNumberOfDays) {
        try {
            this.maxDays = Integer.parseInt(maxNumberOfDays);
        } catch (NumberFormatException e) {
            LogLog.warn("Could not parse numberOfDays-string " + maxNumberOfDays + ", using 7 as default", e);
            this.maxDays = 7;
        }
    }

    public boolean shouldCompress() {
        return compressBackups;
    }

    public String getCompressBackups() {
        return String.valueOf(compressBackups);
    }

    public void setCompressBackups(String compressBackups) {
        this.compressBackups = "YES".equalsIgnoreCase(compressBackups) ||
            "TRUE".equalsIgnoreCase(compressBackups);
    }

    public void setFile(final String filename) {
        makePath(filename);
        super.setFile(filename);
    }

    public void makePath(final String filename) {
        File dir;

        try {
            URL url = new URL(filename.trim());
            dir = new File(url.getFile()).getParentFile();
        } catch (MalformedURLException e) {
            dir = new File(filename.trim()).getParentFile();
        }

        if (!dir.exists()) {
            boolean success = dir.mkdirs();

            if (!success) {
                LogLog.error("Failed to create directory structure: " + dir);
            }
        }
    }

    class RollingCalendar extends GregorianCalendar {
        private static final long serialVersionUID = -3560331770601814177L;
        int type = TOP_OF_TROUBLE;

        RollingCalendar() {
            super();
        }

        RollingCalendar(TimeZone tz, Locale locale) {
            super(tz, locale);
        }

        void setType(int type) {
            this.type = type;
        }

        public long getNextCheckMillis(Date now) {
            return getNextCheckDate(now).getTime();
        }

        public Date getNextCheckDate(Date now) {
            this.setTime(now);

            switch (type) {
            case TOP_OF_MINUTE:
                this.set(Calendar.SECOND, 0);
                this.set(Calendar.MILLISECOND, 0);
                this.add(Calendar.MINUTE, 1);

                break;

            case TOP_OF_HOUR:
                this.set(Calendar.MINUTE, 0);
                this.set(Calendar.SECOND, 0);
                this.set(Calendar.MILLISECOND, 0);
                this.add(Calendar.HOUR_OF_DAY, 1);

                break;

            case HALF_DAY:
                this.set(Calendar.MINUTE, 0);
                this.set(Calendar.SECOND, 0);
                this.set(Calendar.MILLISECOND, 0);

                int hour = get(Calendar.HOUR_OF_DAY);

                if (hour < 12) {
                    this.set(Calendar.HOUR_OF_DAY, 12);
                } else {
                    this.set(Calendar.HOUR_OF_DAY, 0);
                    this.add(Calendar.DAY_OF_MONTH, 1);
                }

                break;

            case TOP_OF_DAY:
                this.set(Calendar.HOUR_OF_DAY, 0);
                this.set(Calendar.MINUTE, 0);
                this.set(Calendar.SECOND, 0);
                this.set(Calendar.MILLISECOND, 0);
                this.add(Calendar.DATE, 1);

                break;

            case TOP_OF_WEEK:
                this.set(Calendar.DAY_OF_WEEK, getFirstDayOfWeek());
                this.set(Calendar.HOUR_OF_DAY, 0);
                this.set(Calendar.MINUTE, 0);
                this.set(Calendar.SECOND, 0);
                this.set(Calendar.MILLISECOND, 0);
                this.add(Calendar.WEEK_OF_YEAR, 1);

                break;

            case TOP_OF_MONTH:
                this.set(Calendar.DATE, 1);
                this.set(Calendar.HOUR_OF_DAY, 0);
                this.set(Calendar.MINUTE, 0);
                this.set(Calendar.SECOND, 0);
                this.set(Calendar.MILLISECOND, 0);
                this.add(Calendar.MONTH, 1);

                break;

            default:
                throw new IllegalStateException("Unknown periodicity type.");
            }

            return getTime();
        }
    }
}

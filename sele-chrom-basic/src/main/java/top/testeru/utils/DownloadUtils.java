package top.testeru.utils;

public class DownloadUtils {

    private static final String BINTRAY_TEMPLATE =
            "https://dl.bintray.com/qameta/generic/io/qameta/allure/allure/%s/allure-%s.zip";

    private static final String BY_DOT = "\\.";

    private DownloadUtils() {
        throw new IllegalStateException("do not instance");
    }

    public static String getAllureDownloadUrl(final String version, final String downloadUrl) {
        if (downloadUrl != null) {
            return downloadUrl;
        }
        if (versionCompare(version, "2.8.0") < 0) {
            return BINTRAY_TEMPLATE;
        }
        return null;
    }

    private static Integer versionCompare(final String first, final String second) {
        final String[] firstVersions = first.split(BY_DOT);
        final String[] secondVersions = second.split(BY_DOT);
        int i = 0;
        while (i < firstVersions.length && i < secondVersions.length
                && firstVersions[i].equals(secondVersions[i])) {
            i++;
        }
        if (i < firstVersions.length && i < secondVersions.length) {
            final int diff =
                    Integer.valueOf(firstVersions[i]).compareTo(Integer.valueOf(secondVersions[i]));
            return Integer.signum(diff);
        } else {
            return Integer.signum(firstVersions.length - secondVersions.length);
        }
    }

}
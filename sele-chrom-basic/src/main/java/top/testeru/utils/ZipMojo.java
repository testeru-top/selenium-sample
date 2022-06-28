//package top.testeru.utils;
//
//
//import org.apache.maven.plugins.annotations.Parameter;
//import org.apache.maven.reporting.MavenReportException;
//import org.slf4j.Logger;
//
//import java.io.IOException;
//import java.nio.file.Paths;
//
//import static java.lang.invoke.MethodHandles.lookup;
//import static org.slf4j.LoggerFactory.getLogger;
//
///**
// * @program: selenium-sample
// * @author: testeru.top
// * @description:
// * @Version 1.0
// * @create: 2022/6/27 15:52
// */
//public class ZipMojo {
//
//    public static final Logger logger = getLogger(lookup().lookupClass());
//
//    @Parameter(property = "allure.install.directory", defaultValue = "${project.basedir}/.allure")
//    private String installDirectory;
//
//
//    private void installAllure() throws MavenReportException {
//        try {
//            final AllureCommandline commandline =
//                    new AllureCommandline(Paths.get(installDirectory), reportVersion);
//            log.info(String.format("Allure installation directory %s", installDirectory));
//            getLog().info(String.format("Try to finding out allure %s", commandline.getVersion()));
//
//            if (commandline.allureNotExists()) {
//                final String downloadUrl = DownloadUtils
//                        .getAllureDownloadUrl(commandline.getVersion(), allureDownloadUrl);
//                if (downloadUrl == null) {
//                    commandline.downloadWithMaven(session, dependencyResolver);
//                } else {
//                    getLog().info("Downloading allure commandline from " + downloadUrl);
//                    commandline.download(downloadUrl, ProxyUtils.getProxy(session, decrypter));
//                    getLog().info("Downloading allure commandline complete");
//                }
//            }
//        } catch (IOException e) {
//            getLog().error("Installation error", e);
//            throw new MavenReportException("Can't install allure", e);
//        }
//    }
//
//}

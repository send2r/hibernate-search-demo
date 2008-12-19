/*
* Copyright (c) 2007 Pyramid Consulting. All rights reserved.
* Created on Mar 27, 2007
* $Id: BrowserDetector.java,v 1.1 2007/11/16 01:47:32 tientrinh.nguyen Exp $
*
*/
package demo.hibernatesearch.taglib;

import java.util.ArrayList;
import java.util.List;

/**
 * <Briefly describing the purpose of the class/interface...>
 * @author $Author: tientrinh.nguyen $
 */
public class BrowserDetector {
    public static final String BROWSER_OMNIWEB = "OmniWeb";
    public static final String BROWSER_SAFARI = "Safari";
    public static final String BROWSER_OPARA = "Opera";
    public static final String BROWSER_ICAB = "iCab";
    public static final String BROWSER_KONQUEROR = "Konqueror";
    public static final String BROWSER_FIREFOX = "Firefox";
    public static final String BROWSER_CAMINO = "Camino";
    public static final String BROWSER_NETSCAPE = "Netscape";
    public static final String BROWSER_IE = "IE";
    public static final String BROWSER_MOZILLA = "Mozilla";

    public static final String OS_WINDOWS = "Windows";
    public static final String OS_MAC = "Mac";
    public static final String OS_LINUX = "Linux";

    private static List<DetectorEntry> browserEntries;
    private static List<DetectorEntry> osEntries;

    static {
        browserEntries = new ArrayList<DetectorEntry>();
        browserEntries.add(new DetectorEntry("OmniWeb", BROWSER_OMNIWEB));
        browserEntries.add(new DetectorEntry("Apple", BROWSER_SAFARI));
        browserEntries.add(new DetectorEntry("Opera", BROWSER_OPARA));
        browserEntries.add(new DetectorEntry("iCab", BROWSER_ICAB));
        browserEntries.add(new DetectorEntry("KDE", BROWSER_KONQUEROR));
        browserEntries.add(new DetectorEntry("Firefox", BROWSER_FIREFOX));
        browserEntries.add(new DetectorEntry("Camino", BROWSER_CAMINO));
        browserEntries.add(new DetectorEntry("Netscape", BROWSER_NETSCAPE));
        browserEntries.add(new DetectorEntry("MSIE", BROWSER_IE));
        browserEntries.add(new DetectorEntry("Gecko", BROWSER_MOZILLA));

        osEntries = new ArrayList<DetectorEntry>();
        osEntries.add(new DetectorEntry("Win", OS_WINDOWS));
        osEntries.add(new DetectorEntry("Mac", OS_MAC));
        osEntries.add(new DetectorEntry("Linux", OS_LINUX));
    }

    private String browser;
    private String version;
    private String os;

    public BrowserDetector(String userAgent) {
        initialize(userAgent);
    }

    public String getBrowser() {
        return browser;
    }

    public String getOS() {
        return os;
    }

    public String getVersion() {
        return version;
    }

    private void initialize(String userAgent) {
        for(DetectorEntry entry : browserEntries) {
            if(userAgent.indexOf(entry.detectedText) >= 0) {
                this.browser = entry.identity;
                int index = userAgent.indexOf(entry.identity);
                if(index >= 0) {
                    this.version = userAgent.substring(index + entry.identity.length() + 1);
                    index = this.version.indexOf(' ');
                    if(index >= 0) {
                        this.version = this.version.substring(0, index);
                    }
                    this.version = this.version.replaceAll("[\\t\\s;\\:]+", "");
                }
                break;
            }
        }

        for(DetectorEntry entry : osEntries) {
            if(userAgent.indexOf(entry.detectedText) >= 0) {
                this.os = entry.identity;
                break;
            }
        }
    }

    static class DetectorEntry {
        String detectedText;
        String identity;
        DetectorEntry(String detectedText, String identity) {
            this.detectedText = detectedText;
            this.identity = identity;
        }
    }
}

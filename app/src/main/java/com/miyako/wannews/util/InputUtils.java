package com.miyako.wannews.util;

import com.miyako.architecture.util.LogUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author Miyako
 * @Date 2021-04-29-0029
 */
public class InputUtils {

    private static final String TAG = InputUtils.class.getSimpleName();

    public static boolean isAllSpace(String s) {
        return s.replaceAll("\\s", "").length() == 0;
    }

    public static boolean isMAC(String str) {
        return str.matches("[0-9a-fA-F][02468ACEace](:[0-9a-fA-F]{2}){5}");
    }

    public static boolean isLanIp(String ip) {
        Pattern reg = Pattern.compile("^(10\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})|(172\\.((1[6-9])|(2\\d)|(3[01]))\\.\\d{1,3}\\.\\d{1,3})|(192\\.168\\.\\d{1,3}\\.\\d{1,3})$");
        Matcher match = reg.matcher(ip);
        return match.find();
    }


    // IPv6校验

    public static boolean verifyIPv6(String string) {

        boolean result = false;
        String str = "([0-9a-fA-F]{1,4}:){7,7}([0-9a-fA-F]{1,4}|:)|"+
                "([0-9a-fA-F]{1,4}:){1,6}(:[0-9a-fA-F]{1,4}|:)|"+
                "([0-9a-fA-F]{1,4}:){1,5}((:[0-9a-fA-F]{1,4}){1,2}|:)|"+
                "([0-9a-fA-F]{1,4}:){1,4}((:[0-9a-fA-F]{1,4}){1,3}|:)|"+
                "([0-9a-fA-F]{1,4}:){1,3}((:[0-9a-fA-F]{1,4}){1,4}|:)|"+
                "([0-9a-fA-F]{1,4}:){1,2}((:[0-9a-fA-F]{1,4}){1,5}|:)|"+
                "[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6}|:)|"+
                ":((:[0-9a-fA-F]{1,4}){1,7}|:)";
        System.out.println("final:"+string.matches(str));

        result = string.matches(str);

        // String regHex = "([0-9a-fA-F]{1,4})";
        // //没有双冒号
        // String regIPv6Full = "^(" + regHex + ":){7}" + regHex + "$";
        //
        // System.out.println("regIpv6Full: " + string + "->" + string.matches(regIPv6Full));
        // //双冒号在中间
        // String regIPv6AbWithColon = "^(" + regHex + "(:|::)){0,6}" + regHex
        //         + "$";
        //
        // System.out.println("regIPv6AbWithColon: " + string + "->" + string.matches(regIPv6AbWithColon));
        //
        // //双冒号开头
        // String regIPv6AbStartWithDoubleColon = "^(" + "::(" + regHex
        //         + ":){0,5}" + regHex + ")$";
        // System.out.println("regIPv6AbStartWithDoubleColon: " + string + "->" + string.matches(regIPv6AbStartWithDoubleColon));
        //
        // String regIPv6 = "^(" + regIPv6Full + ")|("
        //         + regIPv6AbStartWithDoubleColon + ")|(" + regIPv6AbWithColon
        //         + ")$";
        //
        //
        // //下面还要处理地址为::的情形和地址包含多于一个的::的情况（非法）
        // if (string.contains(":")) {
        //     if (string.length() <= 39) {
        //         String addressTemp = string;
        //         int doubleColon = 0;
        //         if(string.equals("::"))return true;
        //         while (addressTemp.contains("::")) {
        //             addressTemp = addressTemp.substring(addressTemp
        //                     .indexOf("::") + 2,addressTemp.length());
        //             doubleColon++;
        //         }
        //         if (doubleColon <= 1) {
        //             result = string.matches(regIPv6);
        //         }
        //     }
        // }
        System.out.println(String.format("%s is ipv6:%s", string, result));
        LogUtils.INSTANCE.d(TAG, String.format("%s is ipv6:%s", string, result));
        return result;

    }


    public static final long PREFIX_2000 = 0x2000L << 48;
    public static final long PREFIX_3FFF = 0x3FFFL << 48;
    // 非法地址
    public static final long PREFIX_FE80 = 0xFE80L << 48;
    public static final long PREFIX_2002 = 0x2002L << 48;
    public static final long PREFIX_FEC0 = 0xFEC0L << 48;
    public static final long PREFIX_FC00 = 0xFC00L << 48;
    public static final long PREFIX_FD00 = 0xFD00L << 48;

    public static final long[] ILLEGAL_HEAD = {PREFIX_FE80, PREFIX_2002, PREFIX_FEC0, PREFIX_FC00, PREFIX_FD00};

    public static boolean isValidIPv6Head(String ipv6) {
        boolean result = true;
        for (long addr : ILLEGAL_HEAD) {
            System.out.println("校验开头:"+printLong(addr));
            if (InputUtils.verifyIPv6Head(ipv6, addr)) {
                result = false;
                break;
            }
        }
        System.out.println("input ipv6:" + ipv6);
        System.out.println("isValidIPv6Head:"+result);
        return true;
    }


    public static boolean verifyIPv6Head(String ip, long head) {
        long[] ipAdd = ipv6ToInt(ip);
        LogUtils.INSTANCE.d("miyako", "ip   add:"+Long.toBinaryString(ipAdd[0]));
        LogUtils.INSTANCE.d("miyako", "ip start:"+Long.toBinaryString(head));
        long temp1 = ipAdd[0] & head;
        if (temp1 != head) {
            LogUtils.INSTANCE.d("miyako", "不在范围内");
            return false;
        }
        return true;
    }

    public static boolean verifyIPv6AndGateway(String ip, String gateway, int mask) {
        long[] ipAdd = ipv6ToInt(ip);
        long[] gateWay = ipv6ToInt(gateway);
        long[] maskAdd = new long[2];
        if (mask <= 64) {
            for (int i = 0; i < mask; i++) {
                maskAdd[0] = maskAdd[0] | 1L << (63-i);
                // maskAdd[0] = (maskAdd[0] << 1) | 0x01L;
                // LogUtil.d(TAG, "mask:"+Long.toBinaryString(maskAdd[0]));
            }
            LogUtils.INSTANCE.i(TAG, "ip:"+Long.toBinaryString(ipAdd[0]));
            LogUtils.INSTANCE.i(TAG, "gateway:"+Long.toBinaryString(gateWay[0]));
            System.out.println("ipv6   :"+printLong(ipAdd[0]));
            System.out.println("gateway:"+printLong(gateWay[0]));
            System.out.println("mask   :"+printLong(maskAdd[0]));
            long temp1 = ipAdd[0] & maskAdd[0];
            long temp2 = gateWay[0] & maskAdd[0];
            if (temp1 != temp2) {
                LogUtils.INSTANCE.d("miyako", "不同网段");
                System.out.println("不同网段");
                return false;
            }
        }
        return true;
    }


    private static byte toByte(char c) {
        byte res = 0x00;
        switch (c) {
            case '0': res = 0x00;break;
            case '1': res = 0x01;break;
            case '2': res = 0x02;break;
            case '3': res = 0x03;break;
            case '4': res = 0x04;break;
            case '5': res = 0x05;break;
            case '6': res = 0x06;break;
            case '7': res = 0x07;break;
            case '8': res = 0x08;break;
            case '9': res = 0x09;break;
            case 'a': case 'A': res = 0x0a;break;
            case 'b': case 'B': res = 0x0b;break;
            case 'c': case 'C': res = 0x0c;break;
            case 'd': case 'D': res = 0x0d;break;
            case 'e': case 'E': res = 0x0e;break;
            case 'f': case 'F': res = 0x0f;break;
        }
        return res;
    }

    private static int toByteArr(String addr) {
        int res = 0x00;
        if (addr.length() > 4) {
            return res;
        }
        int l = 0;
        int len = addr.length();
        for (int i = 0; i < len; i++) {
            char c = addr.charAt(i);
            l = toByte(c) | l << 4;
        }
        // LogUtil.d("miyako","int:"+Integer.toBinaryString(l));
        res = (byte) (l >> 8 & 0xff);
        res = (byte) (l & 0xff);
        return l;
    }

    public static long[] ipv6ToInt(String ipv6){
        int compressIndex = ipv6.indexOf("::");
        int[] ipv6Bytes = new int[8];
        long[] res = new long[2];
        if (compressIndex != -1){
            // 压缩前1
            String part1s = ipv6.substring(0, compressIndex);
            String[] split1 = part1s.split(":");
            // 压缩后1
            String part2s = ipv6.substring(compressIndex + 2);
            String[] split2 = part2s.split(":");
            int sub = 8 - split1.length - split2.length;
            int idx = 0;
            if (split1.length > 0) {
                for (String s : split1) {
                    // LogUtil.d("miyako", "addr:" + s);
                    int addr = toByteArr(s);
                    ipv6Bytes[idx] = addr & 0xffff;
                    // ipv6Bytes[idx] = (ipv6Bytes[idx] << 8 | addr[1]);
                    idx += 1;
                }
            }
            idx = idx + sub;
            if (split2.length > 0) {
                for (String s : split2) {
                    // LogUtil.d("miyako", "addr:" + s);
                    int addr = toByteArr(s);
                    ipv6Bytes[idx] = addr & 0xffff;
                    idx += 1;
                }
            }
            for (int i = 0; i < ipv6Bytes.length; i++) {
                if (i < 4) {
                    res[0] = res[0] << 16 | ipv6Bytes[i];
                } else {
                    res[1] = res[1] << 16 | ipv6Bytes[i];
                }
            }
        }
        System.out.println("ipv6 str:"+ipv6);
        System.out.println("ipv6 high byte:"+printLong(res[0]));
        System.out.println("ipv6 low  byte:"+printLong(res[1]));
        return res;
    }

    private static String printLong(long value) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 64; i++) {
            if (i%4==0) {
                sb.append(" ");
            }
            sb.append(value & 0x1);

            value=value>>1;
        }
        String str = sb.toString();
        // System.out.println("sb:"+str);
        str = sb.reverse().toString();
        // System.out.println("final:"+str);
        return str;
    }

    private static boolean verifyIPv6Prefix(String mask, int start, int end) {
        boolean result = false;
        try {
            int len = Integer.parseInt(mask);
            result = len >= start && len <= end;
        } catch (NumberFormatException e) {
            LogUtils.INSTANCE.d("miyako", "转换ipv6前缀异常: " + mask);
            result = false;
        }
        return result;
    }

    private final static int INADDR4SZ = 4;
    private final static int INADDR16SZ = 16;
    private final static int INT16SZ = 2;

    public static boolean isIPv6LiteralAddress(String src) {
        LogUtils.INSTANCE.d(TAG, "ipv6:"+src);
        return textToNumericFormatV6(src) != null;
    }

    public static byte[] textToNumericFormatV6(String src) {
        // Shortest valid string is "::", hence at least 2 chars
        if (src.length() < 2) {
            return null;
        }

        int colonp;
        char ch;
        boolean saw_xdigit;
        int val;
        char[] srcb = src.toCharArray();
        byte[] dst = new byte[INADDR16SZ];

        int srcb_length = srcb.length;
        int pc = src.indexOf ("%");
        if (pc == srcb_length -1) {
            return null;
        }

        if (pc != -1) {
            srcb_length = pc;
        }

        colonp = -1;
        int i = 0, j = 0;
        /* Leading :: requires some special handling. */
        if (srcb[i] == ':')
            if (srcb[++i] != ':')
                return null;
        int curtok = i;
        saw_xdigit = false;
        val = 0;
        while (i < srcb_length) {
            ch = srcb[i++];
            int chval = Character.digit(ch, 16);
            if (chval != -1) {
                val <<= 4;
                val |= chval;
                if (val > 0xffff)
                    return null;
                saw_xdigit = true;
                continue;
            }
            if (ch == ':') {
                curtok = i;
                if (!saw_xdigit) {
                    if (colonp != -1)
                        return null;
                    colonp = j;
                    continue;
                } else if (i == srcb_length) {
                    return null;
                }
                if (j + INT16SZ > INADDR16SZ)
                    return null;
                dst[j++] = (byte) ((val >> 8) & 0xff);
                dst[j++] = (byte) (val & 0xff);
                saw_xdigit = false;
                val = 0;
                continue;
            }
            if (ch == '.' /*&& ((j + INADDR4SZ) <= INADDR16SZ)*/) {
                return null;
                // String ia4 = src.substring(curtok, srcb_length);
                // /* check this IPv4 address has 3 dots, ie. A.B.C.D */
                // int dot_count = 0, index=0;
                // while ((index = ia4.indexOf ('.', index)) != -1) {
                //     dot_count ++;
                //     index ++;
                // }
                // if (dot_count != 3) {
                //     return null;
                // }
                // byte[] v4addr = textToNumericFormatV4(ia4);
                // if (v4addr == null) {
                //     return null;
                // }
                // for (int k = 0; k < INADDR4SZ; k++) {
                //     dst[j++] = v4addr[k];
                // }
                // saw_xdigit = false;
                // break;  /* '\0' was seen by inet_pton4(). */
            }
            return null;
        }
        if (saw_xdigit) {
            if (j + INT16SZ > INADDR16SZ)
                return null;
            dst[j++] = (byte) ((val >> 8) & 0xff);
            dst[j++] = (byte) (val & 0xff);
        }

        if (colonp != -1) {
            int n = j - colonp;

            if (j == INADDR16SZ)
                return null;
            for (i = 1; i <= n; i++) {
                dst[INADDR16SZ - i] = dst[colonp + n - i];
                dst[colonp + n - i] = 0;
            }
            j = INADDR16SZ;
        }
        if (j != INADDR16SZ)
            return null;
        byte[] newdst = convertFromIPv4MappedAddress(dst);
        if (newdst != null) {
            return newdst;
        } else {
            return dst;
        }
    }

    public static byte[] convertFromIPv4MappedAddress(byte[] addr) {
        if (isIPv4MappedAddress(addr)) {
            byte[] newAddr = new byte[INADDR4SZ];
            System.arraycopy(addr, 12, newAddr, 0, INADDR4SZ);
            return newAddr;
        }
        return null;
    }

    private static boolean isIPv4MappedAddress(byte[] addr) {
        if (addr.length < INADDR16SZ) {
            return false;
        }
        if ((addr[0] == 0x00) && (addr[1] == 0x00) &&
                (addr[2] == 0x00) && (addr[3] == 0x00) &&
                (addr[4] == 0x00) && (addr[5] == 0x00) &&
                (addr[6] == 0x00) && (addr[7] == 0x00) &&
                (addr[8] == 0x00) && (addr[9] == 0x00) &&
                (addr[10] == (byte)0xff) &&
                (addr[11] == (byte)0xff))  {
            return true;
        }
        return false;
    }

    public static boolean isWebsite(String url) {
        return url.matches("^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$");
    }
}

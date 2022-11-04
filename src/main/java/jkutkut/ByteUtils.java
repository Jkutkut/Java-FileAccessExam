package jkutkut;

import java.util.Objects;

public class ByteUtils {
    public static int sizeof(byte b) {
        return Byte.BYTES;
    }

    public static int sizeof(short s) {
        return Short.BYTES;
    }

    public static int sizeof(int i) {
        return Integer.BYTES;
    }

    public static int sizeof(long l) {
        return Long.BYTES;
    }

    public static int sizeof(float f) {
        return Float.BYTES;
    }

    public static int sizeof(double d) {
        return Double.BYTES;
    }

    public static int sizeof(boolean b) {
        return Byte.BYTES; // boolean is 1 byte
    }

    public static int sizeof(char c) {
        return Character.BYTES;
    }

    public static int sizeofString(int length) {
        return sizeof('a') * length;
    }

    public static int sizeof(String s) {
        return sizeofString(s.length());
    }

    // Converter methods

    // To byte array

    public static byte[] toBytes(byte b) {
        return new byte[] { b };
    }

    public static byte[] toBytes(short s) {
        byte[] bytes = new byte[sizeof(s)];
        bytes[0] = (byte) (s >> 8);
        bytes[1] = (byte) (s);
        return bytes;
    }

    public static byte[] toBytes(int i) {
        byte[] bytes = new byte[sizeof(i)];
        bytes[0] = (byte) (i >> 24);
        bytes[1] = (byte) (i >> 16);
        bytes[2] = (byte) (i >> 8);
        bytes[3] = (byte) (i);
        return bytes;
    }

    public static byte[] toBytes(long l) {
        byte[] bytes = new byte[sizeof(l)];
        bytes[0] = (byte) (l >> 56);
        bytes[1] = (byte) (l >> 48);
        bytes[2] = (byte) (l >> 40);
        bytes[3] = (byte) (l >> 32);
        bytes[4] = (byte) (l >> 24);
        bytes[5] = (byte) (l >> 16);
        bytes[6] = (byte) (l >> 8);
        bytes[7] = (byte) (l);
        return bytes;
    }

    public static byte[] toBytes(float f) {
        return toBytes(Float.floatToIntBits(f));
    }

    public static byte[] toBytes(double d) {
        return toBytes(Double.doubleToLongBits(d));
    }

    public static byte[] toBytes(boolean b) {
        return new byte[] { (byte) (b ? 1 : 0) };
    }

    public static byte[] toBytes(char c) {
        byte[] bytes = new byte[sizeof(c)];
        bytes[0] = (byte) (c >> 8);
        bytes[1] = (byte) (c);
        return bytes;
    }

    public static byte[] toBytes(String s) {
        return s.getBytes();
    }

    public static byte[] toBytes(String s, int length) {
        StringBuffer sb = new StringBuffer(s);
        sb.setLength(length);
        return toBytes(sb.toString());
    }

    // From byte array

    public static byte toByte(byte[] bytes) {
        return bytes[0];
    }

    public static short toShort(byte[] bytes) {
        return (short) ((bytes[0] << 8) | (bytes[1] & 0xFF));
    }

    public static int toInt(byte[] bytes) {
        return (bytes[0] << 24) | ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF);
    }

    public static long toLong(byte[] bytes) {
        return ((long) bytes[0] << 56) | ((long) (bytes[1] & 0xFF) << 48) |
                ((long) (bytes[2] & 0xFF) << 40) | ((long) (bytes[3] & 0xFF) << 32) |
                ((long) (bytes[4] & 0xFF) << 24) | ((long) (bytes[5] & 0xFF) << 16) |
                ((long) (bytes[6] & 0xFF) << 8) | (long) (bytes[7] & 0xFF);
    }

    public static float toFloat(byte[] bytes) {
        return Float.intBitsToFloat(toInt(bytes));
    }

    public static double toDouble(byte[] bytes) {
        return Double.longBitsToDouble(toLong(bytes));
    }

    public static boolean toBoolean(byte[] bytes) {
        return bytes[0] == 1;
    }

    public static char toChar(byte[] bytes) {
        return (char) ((bytes[0] << 8) | (bytes[1] & 0xFF));
    }

    public static String toString(byte[] bytes) {
        char[] chars = new char[bytes.length / Character.BYTES];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = toChar(new byte[] { bytes[i * 2], bytes[i * 2 + 1] });
        }
        return new String(chars);
    }

    // Tools

    public static int memcpy(byte[] dest, int destOffset, byte[] src, int srcOffset, int length) {
        int copyLength = Math.min(length, src.length - srcOffset);
        System.arraycopy(src, srcOffset, dest, destOffset, copyLength);
        for (int i = copyLength; i < length; i++) {
            dest[destOffset + i] = 0;
        }
//        System.out.println("destOffset: " + destOffset + " srcOffset: " + srcOffset + " length: " + length + " copyLength: " + copyLength + " ");
        return destOffset + length;
    }

    public static int memcpy(byte[] dest, int destOffset, byte[] src) {
//        System.out.println("destOffset: " + destOffset + ", src.length: " + src.length);
        return memcpy(dest, destOffset, src, 0, src.length);
    }

    public static int memset(byte[] dest, byte src, int length) {
        for (int i = 0; i < length; i++) {
            dest[i] = src;
        }
        return length;
    }
}



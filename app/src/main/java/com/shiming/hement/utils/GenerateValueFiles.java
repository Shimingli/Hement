package com.shiming.hement.utils;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * <p>
 *  适配工具类 sw
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2019/1/8 09:30
 */

class GenerateValueFiles {
    private int baseW;
    private int baseH;

    private String dirStr = "./res";

    private final static String WTemplate = "<dimen name=\"dp_{0}\">{1}dp</dimen>\n";
    private final static String HTemplate = "<dimen name=\"sp_{0}\">{1}sp</dimen>\n";

    /**
     * {0}-HEIGHT
     */
    private final static String VALUE_TEMPLATE = "values-{0}x{1}";

    private static final String SUPPORT_DIMESION = "800,1280;1080,1812;1080,1920;1011,1920;1440,2560;";

    private String supportStr = SUPPORT_DIMESION;

    public GenerateValueFiles(int baseX, int baseY, String supportStr) {
        this.baseW = baseX;
        this.baseH = baseY;

        if (!this.supportStr.contains(baseX + "," + baseY)) {
            this.supportStr += baseX + "," + baseY + ";";
        }

        this.supportStr += validateInput(supportStr);

        System.out.println(supportStr);

        File dir = new File(dirStr);
        if (!dir.exists()) {
            dir.mkdir();

        }
        System.out.println(dir.getAbsoluteFile());

    }

    /**
     * @param supportStr
     *            w,h_...w,h;
     * @return
     */
    private String validateInput(String supportStr) {
        StringBuffer sb = new StringBuffer();
        String[] vals = supportStr.split("_");
        int w = -1;
        int h = -1;
        String[] wh;
        for (String val : vals) {
            try {
                if (val == null || val.trim().length() == 0)
                    continue;

                wh = val.split(",");
                w = Integer.parseInt(wh[0]);
                h = Integer.parseInt(wh[1]);
            } catch (Exception e) {
                System.out.println("skip invalidate params : w,h = " + val);
                continue;
            }
            sb.append(w + "," + h + ";");
        }

        return sb.toString();
    }

    public void generate() {
        String[] vals = supportStr.split(";");
        for (String val : vals) {
            String[] wh = val.split(",");
            generateXmlFile(Integer.parseInt(wh[0]), Integer.parseInt(wh[1]));
        }

    }

    private void generateXmlFile(int w, int h) {

        StringBuffer sbForWidth = new StringBuffer();
        sbForWidth.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sbForWidth.append("<resources>");
        float cellw = w * 1.0f / baseW;


        System.out.println("width : " + w + "," + baseW + "," + cellw);
        for (int i = 1; i < baseW; i++) {
            sbForWidth.append(WTemplate.replace("{0}", i + "").replace("{1}",
                    change(cellw * i) + ""));
        }
        sbForWidth.append(WTemplate.replace("{0}", baseW + "").replace("{1}",
                w + ""));
        sbForWidth.append("</resources>");

        StringBuffer sbForHeight = new StringBuffer();
        sbForHeight.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sbForHeight.append("<resources>");
        float cellh = h *1.0f/ baseH;
        System.out.println("height : "+ h + "," + baseH + "," + cellh);
        for (int i = 1; i < baseH; i++) {
            sbForHeight.append(HTemplate.replace("{0}", i + "").replace("{1}",
                    change(cellh * i) + ""));
        }
        sbForHeight.append(HTemplate.replace("{0}", baseH + "").replace("{1}",
                h + ""));
        sbForHeight.append("</resources>");

        File fileDir = new File(dirStr + File.separator
                + VALUE_TEMPLATE.replace("{0}", h + "")//
                .replace("{1}", w + ""));
        fileDir.mkdir();

        File layxFile = new File(fileDir.getAbsolutePath(), "dimens_x.xml");
        File layyFile = new File(fileDir.getAbsolutePath(), "dimens_y.xml");
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(layxFile));
            pw.print(sbForWidth.toString());
            pw.close();
            pw = new PrintWriter(new FileOutputStream(layyFile));
            pw.print(sbForHeight.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * 显示器的逻辑密度，这是【独立的像素密度单位（首先明白dip是个单位）】的一个缩放因子，
     * 在屏幕密度大约为160dpi的屏幕上，一个dip等于一个px,这个提供了系统显示器的一个基线（这句我实在翻译不了）。
     * 例如：屏幕为240*320的手机屏幕，其尺寸为 1.5"*2"  也就是1.5英寸乘2英寸的屏幕
     * 它的dpi（屏幕像素密度，也就是每英寸的像素数，dpi是dot per inch的缩写）大约就为160dpi，
     * 所以在这个手机上dp和px的长度（可以说是长度，最起码从你的视觉感官上来说是这样的）是相等的。
     * 因此在一个屏幕密度为160dpi的手机屏幕上density的值为1，而在120dpi的手机上为0.75等等
     * （这里有一句话没翻译，实在读不通顺，不过通过下面的举例应该能看懂）
     * 例如：一个240*320的屏幕尽管他的屏幕尺寸为1.8"*1.3",（我算了下这个的dpi大约为180dpi多点）
     * 但是它的density还是1(也就是说取了近似值)
     * 然而，如果屏幕分辨率增加到320*480 但是屏幕尺寸仍然保持1.5"*2" 的时候（和最开始的例子比较）
     * 这个手机的density将会增加（可能会增加到1.5）
     */
    public static int px2dp(Context context, float pxValue) {
        // E/@@@: 230  1.4375    dpi      1920  1011
//        Log.e("@@@", dm.densityDpi + "  " + dm.density);

        //商米系统上为 1.4375
        final float scale =  context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     *
     * @param a
     * @return
     */
    public static float change(float a) {
//        int temp = (int) (a * 100);
        float temp = (float) (a/1.4375+0.5);
        return temp;
    }

    /**
     * 这种方式 废弃掉
     * @param a
     * @return
     */
    public static float changeOld(float a) {
        int temp = (int) (a * 100);
        return temp / 100f;
    }

    public static void main(String[] args) {
        int baseW = 1011;
        int baseH = 1920;
        String addition = "";
        try {
            if (args.length >= 3) {
                baseW = Integer.parseInt(args[0]);
                baseH = Integer.parseInt(args[1]);
                addition = args[2];
            } else if (args.length >= 2) {
                baseW = Integer.parseInt(args[0]);
                baseH = Integer.parseInt(args[1]);
            } else if (args.length >= 1) {
                addition = args[0];
            }
        } catch (NumberFormatException e) {

            System.err
                    .println("right input params : java -jar xxx.jar width height w,h_w,h_..._w,h;");
            e.printStackTrace();
            System.exit(-1);
        }

        new GenerateValueFiles(baseW, baseH, addition).generate();
    }
}

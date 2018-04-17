package accountancy.view.components;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class ColorPicker {

    private ArrayList<ArrayList<Color>> allColors = new ArrayList<>();

    private ColorPicker() {

        ArrayList<Color> green1 = new ArrayList<>();
        allColors.add(green1);
        ArrayList<Color> orange1 = new ArrayList<>();
        allColors.add(orange1);
        ArrayList<Color> blue1 = new ArrayList<>();
        allColors.add(blue1);
        ArrayList<Color> yellow = new ArrayList<>();
        allColors.add(yellow);
        ArrayList<Color> pink1 = new ArrayList<>();
        allColors.add(pink1);
        ArrayList<Color> gray = new ArrayList<>();
        allColors.add(gray);
        ArrayList<Color> purple1 = new ArrayList<>();
        allColors.add(purple1);
        ArrayList<Color> red1 = new ArrayList<>();
        allColors.add(red1);
        ArrayList<Color> green2 = new ArrayList<>();
        allColors.add(green2);
        ArrayList<Color> orange2 = new ArrayList<>();
        allColors.add(orange2);
        ArrayList<Color> blue2 = new ArrayList<>();
        allColors.add(blue2);
        ArrayList<Color> pink2 = new ArrayList<>();
        allColors.add(pink2);
        ArrayList<Color> purple2 = new ArrayList<>();
        allColors.add(purple2);
        ArrayList<Color> red2 = new ArrayList<>();
        allColors.add(red2);


        green1.add(new Color(178, 237, 123));
        green1.add(new Color(184, 249, 87));
        green1.add(new Color(148, 204, 79));
        green1.add(new Color(123, 191, 59));
        green1.add(new Color(74, 249, 112));
        green1.add(new Color(62, 196, 89));
        green1.add(new Color(98, 183, 45));
        green1.add(new Color(91, 201, 32));

        green2.add(new Color(81, 198, 51));
        green2.add(new Color(94, 219, 37));
        green2.add(new Color(2, 226, 77));
        green2.add(new Color(9, 132, 46));
        green2.add(new Color(51, 183, 51));
        green2.add(new Color(84, 142, 12));
        green2.add(new Color(13, 173, 80));

        orange1.add(new Color(235, 107, 0));
        orange1.add(new Color(224, 201, 134));
        orange1.add(new Color(237, 161, 94));
        orange1.add(new Color(204, 142, 67));
        orange1.add(new Color(191, 143, 61));

        orange2.add(new Color(201, 92, 4));
        orange2.add(new Color(216, 111, 13));
        orange2.add(new Color(193, 64, 0));
        orange2.add(new Color(140, 42, 9));
        orange2.add(new Color(186, 96, 0));

        yellow.add(new Color(204, 250, 0));
        yellow.add(new Color(172, 242, 75));
        yellow.add(new Color(239, 205, 93));
        yellow.add(new Color(218, 221, 2));
        yellow.add(new Color(229, 226, 50));
        yellow.add(new Color(232, 216, 76));
        yellow.add(new Color(219, 209, 67));
        yellow.add(new Color(234, 234, 18));
        yellow.add(new Color(196, 161, 7));
        yellow.add(new Color(237, 237, 18));
        yellow.add(new Color(211, 195, 19));
        yellow.add(new Color(226, 167, 2));
        yellow.add(new Color(219, 139, 19));
        yellow.add(new Color(229, 155, 6));

        gray.add(new Color(47, 43, 48));
        gray.add(new Color(79, 67, 68));
        gray.add(new Color(88, 92, 102));
        gray.add(new Color(87, 92, 96));
        gray.add(new Color(88, 104, 109));

        pink1.add(new Color(247, 220, 187));
        pink1.add(new Color(221, 142, 249));
        pink1.add(new Color(212, 123, 242));
        pink1.add(new Color(234, 70, 141));
        pink1.add(new Color(239, 117, 231));

        pink2.add(new Color(239, 88, 169));
        pink2.add(new Color(247, 79, 166));
        pink2.add(new Color(155, 4, 65));
        pink2.add(new Color(209, 10, 116));
        pink2.add(new Color(234, 18, 231));

        blue1.add(new Color(91, 112, 247));
        blue1.add(new Color(0, 229, 204));
        blue1.add(new Color(94, 214, 184));
        blue1.add(new Color(0, 203, 119));
        blue1.add(new Color(170, 197, 239));
        blue1.add(new Color(126, 247, 231));
        blue1.add(new Color(187, 212, 249));
        blue1.add(new Color(138, 234, 204));
        blue1.add(new Color(162, 223, 242));
        blue1.add(new Color(4, 226, 189));

        blue2.add(new Color(37, 102, 155));
        blue2.add(new Color(36, 44, 193));
        blue2.add(new Color(61, 48, 209));
        blue2.add(new Color(6, 88, 132));
        blue2.add(new Color(4, 124, 108));
        blue2.add(new Color(10, 46, 130));
        blue2.add(new Color(14, 37, 142));
        blue2.add(new Color(1, 77, 94));
        blue2.add(new Color(12, 106, 127));
        blue2.add(new Color(3, 64, 114));
        blue2.add(new Color(5, 90, 109));
        blue2.add(new Color(31, 13, 147));

        red1.add(new Color(186, 81, 63));
        red1.add(new Color(247, 175, 198));
        red1.add(new Color(242, 59, 53));
        red1.add(new Color(249, 59, 91));
        red1.add(new Color(234, 125, 103));

        red2.add(new Color(242, 120, 99));
        red2.add(new Color(252, 170, 103));
        red2.add(new Color(132, 24, 0));
        red2.add(new Color(183, 27, 16));
        red2.add(new Color(155, 12, 48));

        purple1.add(new Color(154, 122, 219));
        purple1.add(new Color(170, 9, 191));
        purple1.add(new Color(229, 180, 247));
        purple1.add(new Color(122, 49, 170));
        purple1.add(new Color(219, 89, 197));

        purple2.add(new Color(75, 5, 132));
        purple2.add(new Color(64, 10, 135));
        purple2.add(new Color(95, 6, 150));
        purple2.add(new Color(66, 55, 153));
        purple2.add(new Color(40, 9, 119));
    }

    /**
     * Point d'accès pour l'instance unique du singleton
     */
    public static ColorPicker colorPicker() {

        return ColorPicker.FixedHolder.instance;
    }

    public ArrayList<Color> getColors(int n) throws Exception {

        ArrayList<Color> result = new ArrayList<>();
        if (n <= 0 || n > 14) {
            throw new Exception("n invalid");
        }

        for (int i = 0 ; i < n ; i++) {

            ArrayList<Color> res = new ArrayList<>(allColors.get(i));
            Collections.shuffle(res);
            result.add(res.get(0));
        }

        return result;
    }

    public ArrayList<ArrayList<Color>> getColors(int n, ArrayList<Integer> ms) throws Exception {

        ArrayList<ArrayList<Color>> result = new ArrayList<>();
        if (n <= 0 || n > 14) {
            throw new Exception("n invalid");
        }
        if (ms.size() != n) {
            throw new Exception("ms invalid");
        }
        for (Integer m : ms) {
            if (m <= 0 || m > 5) {
                throw new Exception("m invalid");
            }
        }

        for (int i = 0 ; i < n ; i++) {

            ArrayList<Color> res = new ArrayList<>(allColors.get(i));
            Collections.shuffle(res);
            if (res.size() == ms.get(i)) {
                result.add(res);
            }
            else {
                result.add(new ArrayList<>(res.subList(0, ms.get(i))));
            }
        }

        return result;
    }

    /**
     * Holder
     */
    private static class FixedHolder {

        /**
         * Instance unique non préinitialisée
         */
        private final static ColorPicker instance = new ColorPicker();
    }

}

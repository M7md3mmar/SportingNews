package com.in3.SportData.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ammar
 * @version 1.0 .
 * @since 21/1/2020
 */
public class Utils {
    private static List<String> listSportingWord = new ArrayList<>();

    static {
        String sportingKeyWords = "زمالك,أهلى,منتخب,اسماعيلى, اتحاد, بيراميدز,فيفا, كاف,استاد,تنس الطاوله,محمد صلاح,كرة قدم, ميداليه, دراويش, القلعه البيضاء, ليفربول, ريال مدريد, شيكابالا, ميداليه, أنديه, منتخبات, منتخب, دورى, تريزيجيه, مم أفريقيا, أشبيليه,تصفيات,برشلونه,ذئاب الجبل,مبارايات,ركلة جزاء, كاسونجو, المكس";
        getSportingKeyWords(sportingKeyWords);
    }


    private static void getSportingKeyWords(String words) {

        listSportingWord = java.util.Arrays.asList(words.split(","));


    }

    public static boolean checkSportingWords(String text) {
        for (String word : listSportingWord) {
            if (text.contains(word))
                return true;
        }

        return false;
    }

    public static String prepareSolrTime(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String strDate = formatter.format(date);
        return strDate;

    }

    public static String getSafeString(Object input) {
        return input != null ? String.valueOf(input) : "";
    }
}

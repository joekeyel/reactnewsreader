package my.com.tm.dashboard;

/**
 * Created by user on 29/11/2017.
 */

public class Config {

    public static final String TAG_JSON_ARRAY = "datafaultrateall";
    public static final String TAG_JSON_ARRAY1 = "datafaultrate";
    public static final String TAG_JSON_GATED = "gated";
    public static final String TAG_JSON_SCHEDULE = "ipmsanschedule";


    //FAULT RATE
    public static final String URL_GET_ALL = "http://58.27.84.166/mcconline/MCC%20Online%20Dashboard_Yana/query_faultrate_all.php";
    public static final String URL_LIST = "http://58.27.84.166/mcconline/MCC%20Online%20Dashboard_Yana/query_faultrate_mobile.php";

    public static final String TAG_MIG = "migdate";
    public static final String TAG_REG = "region";
    public static final String TAG_STATE = "stateAND";
    public static final String TAG_TM = "tmnode";
    public static final String TAG_NCAB = "newcabinet";
    public static final String TAG_OCAB = "oldcabinet";
    public static final String TAG_TT = "totaltt";
    public static final String TAG_CLOSED = "totalttclosed";
    public static final String TAG_CAP = "capacity";
    public static final String TAG_FAULT = "faultrate";
    public static final String TAG_PRO = "projecttype";


    //GATED
    public static final String URL_GET = "http://58.27.84.166/mcconline/Mcc%20Online%20V3/proxy_gated.php";

    public static final String TAG_CABID = "cabinet";
    public static final String TAG_IPA = "ipaddress";
    public static final String TAG_STA = "status";
    public static final String TAG_TASK = "task";
    public static final String TAG_TEAM = "team";
    public static final String TAG_SEQ = "sequence";
    public static final String TAG_FINAL = "finalstatus";
    public static final String TAG_DATEMIG = "datem";


    //IPMSAN SCHEDULE
    public static final String URL_GET_SCHEDULE = "http://58.27.84.166/mcconline/MCC%20Online%20Dashboard_Yana/query_dashboard_ipmsanschedule.php";

    public static final String TAG_NO = "No";
    public static final String TAG_REGION = "region";
    public static final String TAG_STATEAND = "stateAND";
    public static final String TAG_SITENAME = "sitename";
    public static final String TAG_OLDCABINET = "oldcabinet";
    public static final String TAG_NEWCABINET = "newcabinet";
    public static final String TAG_PTYPE = "projecttype";
    public static final String TAG_MIGDATE = "migdate";
    public static final String TAG_STOPMONITORING = "stopmonitor";
    public static final String TAG_CKC1 = "ckc1";
    public static final String TAG_CKC2 = "ckc2";
    public static final String TAG_PMW = "pmw";
    public static final String TAG_STATUSCABINET = "statuscabinet";
    public static final String TAG_SUMMARYTAIL = "summarytail";

    // HEADER
    public static final String URL_GET_HEADER = "http://58.27.84.166/mcconline/MCC%20Online%20Dashboard_Yana/query_dashboard_header.php";

}
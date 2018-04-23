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


    public static final String TAG_PLANSUBB= "totalplansubb";
    public static final String TAG_ACTUALSUBB= "totalactualsubb";
    public static final String TAG_PLANFIBER= "totalplanefiber";
    public static final String TAG_ACTUALFIBER = "totalactualefiber";

    // HEADER

    public static final String URL_FAIZ = "http://58.27.84.166/mcconline/MCC%20Online%20V3/query_actual.php";
    public static final String URL_GET_HEADER = "http://58.27.84.166/mcconline/MCC%20Online%20V3/Dashboard_json.php";
    public static final String URL_GET_DASHBOARD ="http://58.27.84.166/mcconline/MCC%20Online%20Dashboard_Yana/query_dashboard_header.php";
    public static final String URL_GET_HANDOVER ="http://58.27.84.166/mcconline/MCC%20Online%20V3/query_handover.php";

    //CABINET MONITORING
    public static final String TAG_JSON_MONITORING = "listmonitoring";
    public static final String URL_GET_MONITORING ="http://58.27.84.166/mcconline/MCC%20Online%20V3/query_listmonitoring.php";
    public static final String TAG_CABINET = "cabinetmonitor";
    public static final String TAG_STATES = "state";
    public static final String TAG_PROJECT = "projecttype";
    public static final String TAG_MIGRATION = "migdate";

    //TOTAL MIGRATED
    public static final String TAG_JSON_MIGRATED = "total";
    public static final String TAG_TOTALMIGRATED = "total";


    //PLAN MIGRATE TODAY
    public static final String URL_GET_PLAN ="http://58.27.84.166/mcconline/MCC%20Online%20V3/Dashboard_details_json.php?projecttypedata=alltype";
    public static final String TAG_PROTYPE = "projecttype";
    public static final String TAG_TMN = "tmnode";
    public static final String TAG_ABBR = "abbr";
    public static final String TAG_STATEEE = "state";
    public static final String TAG_OCABINET = "oldcabinet";
    public static final String TAG_NCABINET = "newcabinet";
    public static final String TAG_TITLE = "planoutagestart";

    //LIST PLAN SUBB
    public static final String TAG_JSON_PLAN = "listplan";
    public static final String URL_GET_PLAN_SUBB ="http://58.27.84.166/mcconline/MCC%20Online%20V3/query_listplan.php";
    public static final String TAG_A = "newsubb";
    public static final String TAG_B = "region";
    public static final String TAG_C = "phase";
    public static final String TAG_D = "planvdsl";


    //LIST ACTUAL SUBB
    public static final String TAG_JSON_ACTUAL = "Subb";
    public static final String URL_GET_ACT_SUBB ="http://58.27.84.166/mcconline/MCC%20Online%20V3/query_listactual.php";
    public static final String TAG_ACTSTATE = "newsubb";
    public static final String TAG_ACTSITE = "oldsubb";
    public static final String ACTABBR = "state";
    public static final String TAG_ACTOLD = "migdate";


    //PLAN FIBER
    public static final String TAG_JSON_PLAN_FIBER = "listplanfiber";
    public static final String URL_GET_PLAN_FIBER ="http://58.27.84.166/mcconline/MCC%20Online%20V3/query_listplan.php";
    public static final String TAG_E = "oldfiber";
    public static final String TAG_F= "newfiber";
    public static final String TAG_G = "state";
    public static final String TAG_H = "planvdsl";


    //ACTUAL FIBER
    public static final String TAG_JSON_ACTUAL_FIBER = "fiber";
    public static final String URL_GET_ACTUAL_FIBER ="http://58.27.84.166/mcconline/MCC%20Online%20V3/query_listactual.php";
    public static final String TAG_STATEACTUALFIBER = "newfiber";
    public static final String TAG_SITEACTUALFIBER = "oldfiber";
    public static final String TAG_ACTUALABBRFIBER = "state";
    public static final String TAG_ACTUALOLDFIBER = "migdate";


    //PLAN SPINE COLO
    public static final String TAG_JSON_PLAN_COLO = "listplan";
    public static final String URL_GET_PLAN_COLO ="http://58.27.84.166/mcconline/MCC%20Online%20V3/Dashboard_details_json.php?projecttypedata=COLO";
    public static final String TAG_PROTYPECOLO = "projecttype";
    public static final String TAG_TMNCOLO= "tmnode";
    public static final String TAG_ABBRCOLO = "abbr";
    public static final String TAG_STATEEECOLO = "state";
    public static final String TAG_OCABINETCOLO = "oldcabinet";
    public static final String TAG_NCABINETCOLO = "newcabinet";
    public static final String TAG_TITLECOLO = "title";

    //ACTUAL SPINE COLO
    public static final String TAG_JSON_ACTUAL_COLO = "colo";
    public static final String URL_GET_ACTUAL_COLO ="http://58.27.84.166/mcconline/MCC%20Online%20V3/query_listactual.php";
    public static final String TAG_STATEACTUALCOLO = "newcolo";
    public static final String TAG_SITEACTUALCOLO = "oldcolo";
    public static final String TAG_ACTUALABBRCOLO = "state";
    public static final String TAG_ACTUALOLDCOLO = "migdate";


    //total balance
    public static final String TOTAL_BALANCE ="http://58.27.84.166/mcconline/MCC%20Online%20V3/query_balance.php";

    //MIGRATED CATEGORY
    public static final String TAG_JSON_CAT = "listcategory";
    public static final String URL_GET_CAT ="http://58.27.84.166/mcconline/MCC%20Online%20V3/query_listcategory.php";
    public static final String TAG_CATPRO = "Projecttype";
    public static final String TAG_CATTOTAL = "total";


    //balance subb
    public static final String URL_BAL_SUBB ="http://58.27.84.166/mcconline/MCC%20Online%20V3/query_listbalancesubb.php";
    public static final String TAG_SUBB = "newsubb";
    public static final String TAG_REGI = "region";
    public static final String TAG_PHASE = "phase";
    public static final String TAG_VDSL = "planvdsl";

    //subb
    public static final String SUBB="http://58.27.84.166/mcconline/MCC%20Online%20V3/query_balancesubb.php";
    //colo
    public static final String COLO="http://58.27.84.166/mcconline/MCC%20Online%20V3/query_balancecolo.php";
    //fiber
    public static final String FIBER="http://58.27.84.166/mcconline/MCC%20Online%20V3/query_balancefiber.php";

    //balance fiber
    public static final String URL_BAL_FIBER ="http://58.27.84.166/mcconline/MCC%20Online%20V3/query_listbalancefiber.php";

    //balance colo
    public static final String URL_BAL_COLO ="http://58.27.84.166/mcconline/MCC%20Online%20V3/query_listbalancecolo.php";

    //list mig cabinet
    public static final String URL_LIST_MIG_CAB ="http://58.27.84.166/mcconline/MCC%20Online%20V3/query_listbycategory.php";

}
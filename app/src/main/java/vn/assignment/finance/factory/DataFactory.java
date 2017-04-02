package vn.assignment.finance.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ray on 3/16/17.
 */

public class DataFactory {
    private static DataFactory instance = null;

    public static DataFactory getInstance(){
        if (instance == null)
            instance = new DataFactory();
        return instance;
    }

    public String getFlag(String code){
        return listFlags().get(code);
    }

    private Map<String, String> listFlags(){
        Map<String, String> flags = new HashMap<>();
        flags.put("AUD","http://ih.constantcontact.com/fs071/1100357195496/img/186.gif?a=1103455179708");
        flags.put("CAD","https://images-na.ssl-images-amazon.com/images/I/51o%2Bc6DHSgL._SY355_.jpg");
        flags.put("CHF","http://www.mapsofworld.com/images/world-countries-flags/french-guiana-flag.gif");
        flags.put("DKK","http://flagpedia.net/data/flags/normal/dk.png");
        flags.put("EUR","https://europa.eu/european-union/sites/europaeu/files/docs/body/flag_yellow_low.jpg");
        flags.put("GBP","http://www.flaginstitute.org/wp/wp-content/uploads/2012/10/UK-Union-Flag.png");
        flags.put("HKD","http://www.worldatlas.com/webimage/flags/countrys/zzzflags/hklarge.gif");
        flags.put("INR","http://www.mapsofindia.com/maps/india/india-flag-1024x600.jpg");
        flags.put("JPY","http://sociorocketnewsen.files.wordpress.com/2014/01/flag-of-japan.png?w=580&h=386");
        flags.put("KRW","http://w4fksg.blu.livefilestore.com/y1peCSyRcR2L65be8eGJSPoaqFnPidpEe6H2mNH0f_zqqTRBxOMtTc11XcFMgdjgDnMO5rs9HssJggO8Z8iq43-2Q/Korean%20Flag.jpg");
        flags.put("KWD","http://www.bmw-me.com/content/dam/bmw/marketMIDEAST/bmw-me_com/Local%20Content/FLAGS/NewFlags/1004503.jpg");
        flags.put("MYR","http://malaysiaflag.facts.co/malaysiaflagof/MalaysiaFlagImage1.png");
        flags.put("NOK","http://lcci.lg.ua/upload/ved/Norvegiay/flag.gif");
        flags.put("RUB","http://www.educationquizzes.com/library/Specialist-Flags/Russia-L.jpg");
        flags.put("SAR","https://www.onlinestores.com/flagdetective/images/download/saudi-arabia-hi.jpg");
        flags.put("SEK","http://flaglane.com/download/swedish-flag/swedish-flag-graphic.png");
        flags.put("SGD","http://singaporeflag.facts.co/SingaporeFlag4.jpg");
        flags.put("THB","https://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/Flag_of_Thailand.svg/500px-Flag_of_Thailand.svg.png");
        flags.put("USD","http://i666.photobucket.com/albums/vv25/Kenji2009/USA%20%20Flag%20and%20Map%20flag/USAFlag.gif");
        return flags;
    }
}

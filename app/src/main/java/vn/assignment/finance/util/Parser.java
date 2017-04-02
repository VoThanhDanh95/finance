package vn.assignment.finance.util;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import vn.assignment.finance.model.ExchangeResponse;

/**
 * Created by Ray on 3/15/17.
 */

public class Parser {

    private static Parser instance;

    public static Parser getInstance() {
        if (instance == null)
            instance = new Parser();
        return instance;
    }

    public List<ExchangeResponse> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readExrateList(parser);
        } finally {
            in.close();
        }
    }

    private List<ExchangeResponse> readExrateList(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<ExchangeResponse> entries = new ArrayList<ExchangeResponse>();

        parser.require(XmlPullParser.START_TAG, "", "ExrateList");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("Exrate")) {
                entries.add(reExrate(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    public ExchangeResponse reExrate(XmlPullParser parser) throws IOException, XmlPullParserException {
        ExchangeResponse model = new ExchangeResponse();
        parser.require(XmlPullParser.START_TAG, "", "Exrate");
        String tag = parser.getName();
        if (tag.equals("Exrate")) {
            model.setCode(parser.getAttributeValue(null, "CurrencyCode"));
            model.setName(parser.getAttributeValue(null, "CurrencyName"));
            model.setBuy(Double.parseDouble(parser.getAttributeValue(null, "Buy")));
            model.setTransfer(Double.parseDouble(parser.getAttributeValue(null, "Transfer")));
            model.setSell(Double.parseDouble(parser.getAttributeValue(null, "Sell")));
            parser.nextTag();
        }
        parser.require(XmlPullParser.END_TAG, "", "Exrate");
        return model;
    }

}

package com.example.data_sparse;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.enableDefaults();

        TextView status1 = (TextView)findViewById(R.id.result); //파싱된 결과확인!


        boolean infrtrlNm = false, inplaceNm = false, inaslAltide = false, inlot= false, inlat = false;

        String frtrlNm = null, placeNm = null,  aslAltide= null, lot = null, lat = null;

        try{
            URL url = new URL("http://apis.data.go.kr/B553662/peakPoiInfoService/getPeakPoiInfoList?"
                    +"serviceKey=fZboF1DVNvRId%2BEAXrLM4fsRtcqrCfccVzQ%2B32PMVnJVGDoc80nJmPVFDdgkCupn3rjznF%2FbBepcHRbmr9%2Fg1A%3D%3D"
                    +"&type=xml"
            ); //검색 URL부분

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();
            InputStream is = url.openStream();
            parser.setInput(new InputStreamReader(is, "UTF-8"));
            parser.next();
            int parserEvent = parser.getEventType();
            System.out.println("파싱시작");

            while (parserEvent != XmlPullParser.END_DOCUMENT){
                switch(parserEvent){
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if(parser.getName().equals("lat")){
                            inlat = true;
                        }
                        if(parser.getName().equals("lot")){
                            inlot = true;
                        }
                        if(parser.getName().equals("aslAltide")){
                            inaslAltide = true;
                        }
                        if(parser.getName().equals("placeNm")){
                            inplaceNm = true;
                        }
                        if(parser.getName().equals("frtrlNm")){
                            infrtrlNm = true;
                        }

                        if(parser.getName().equals("message")){ //message 태그를 만나면 에러 출력
                            status1.setText(status1.getText()+"에러");
                            //여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
                        }
                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if(infrtrlNm){
                            frtrlNm = parser.getText().toString();
                            infrtrlNm = false;
                        }
                        if(inplaceNm){
                            placeNm = parser.getText().toString();
                            inplaceNm = false;
                        }
                        if(inaslAltide){
                            aslAltide = parser.getText().toString();
                            inaslAltide = false;
                        }
                        if(inlat){
                            lat = parser.getText().toString();
                            inlat = false;
                        }
                        if(inlot){
                            lot = parser.getText().toString();
                            inlot = false;
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("item")){
                            status1.setText(status1.getText().toString()+" \n숲길명: "+ frtrlNm +"\n 해발고도: "+aslAltide  +"\n 장소명 : " + placeNm
                                    +"\n 위도 : " + lat +  "\n 경도 : " + lot );

                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch(Exception e){
            status1.setText("에러");
            System.out.println("eeeeeeeeeeeeeeee");

            System.out.println(e);
        }
    }
}
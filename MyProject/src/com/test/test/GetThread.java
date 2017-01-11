package com.test.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.stock.entity.IndexMinuteEntity;

/**
 * Created by xiangleiliu on 2015/8/14.
 */
public class GetThread extends Thread{

//    Calendar instance = Calendar.getInstance();
//
//    public GetThread() {
//
//    }
//
//    Map<String,IndexMinute> map=new HashMap<String,IndexMinute>();//每分钟的对应
//    List<IndexMinute> riseList=new ArrayList<IndexMinute>();
//    List<IndexMinute> dropList=new ArrayList<IndexMinute>();;
//
//    int volume_reversal=30;//反转需要的量能
//
//    public void run(){
//
//        while(true){
//            instance.setTime(new Date());
//            int hour = instance.get(Calendar.HOUR_OF_DAY);
//            int minute = instance.get(Calendar.MINUTE);
//
//            if(hour>15){
//                break;
//            }
//            if((hour>11&&minute>30)&&(hour<13)){
//                continue;
//            }
//            if(hour<9&&minute<30){
//                continue;
//            }
//
//
//            IndexEntity indexEntityFromWeb = getIndexEntityFromWeb();
//            
//            //生成对象
//            IndexMinute indexMinute = makeCurrentIndexMinute(indexEntityFromWeb);
//            map.put(indexMinute.id,indexMinute);//添加到map中记录
//
//            //进行分析
//            
//
//
//        }
//    }
//
//    //从网络获取对象
//    private IndexEntity getIndexEntityFromWeb() {
//		
//		
//    	
//    	return null;
//	}
//
//	public IndexMinute makeCurrentIndexMinute(IndexEntity indexEntityFromWeb) {
//        IndexMinute indexMinute = new IndexMinute(1);
//        String id = getId(instance);
//        indexMinute.init(id, 3800, 2300, indexMinute);
//        return indexMinute;
//    }
//    
//    private String getId(Calendar c) {
//        int hour = c.get(Calendar.HOUR_OF_DAY);
//        int minute =c.get(Calendar.MINUTE);
//        String hourstr=Integer.toString(hour);
//        String minutestr=Integer.toString(minute);
//        hourstr=hourstr.length()==1?"0"+hourstr:hourstr;
//        minutestr=minutestr.length()==1?"0"+minutestr:minutestr;
//        return hourstr+minutestr;
//    }

}

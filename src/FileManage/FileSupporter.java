package FileManage;

import java.util.*;

/**
 * Created by Mhwan on 2016. 6. 12..
 */
public class FileSupporter {
    public ArrayList<String> stringSpliter(String string){
        String[] sarray = string.split("</string>");
        String first = sarray[0];
        sarray[0] = first.substring(first.lastIndexOf("<string"), first.length());

        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i=0; i<sarray.length-1; i++) {
            String str = sarray[i];
            //주석처리된 문장은 없애버림
            arrayList.add(str.replaceAll("<!--.*-->", ""));
        }
        return arrayList;
    }

    public ArrayList<Map<String, Object>> makeKeyValue(ArrayList<ArrayList<String>> list){
        ArrayList<Map<String, Object>> resultmaplist = new ArrayList<Map<String, Object>>();

        for (int i =0; i<list.size(); i++){
            ArrayList<String> ss = list.get(i);
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            for (int j=0; j< ss.size(); j++) {
                String[] arr = ss.get(j).split("[>]");
                String[] key = arr[0].split("\"");
                hashMap.put(key[1], arr[1]);
            }
            resultmaplist.add(i, hashMap);
        }
        return resultmaplist;
    }

    public ArrayList<String> getKeyList(ArrayList<ArrayList<String>> strlist){
        HashSet<String> hashresult = new HashSet<String>();

        for (int i=0; i<strlist.size(); i++){
            ArrayList<String> ss = strlist.get(i);
            for (int j=0; j < ss.size(); j++) {
                String s = ss.get(j);
                String[] arr = s.split("[>]");
                String[] key = arr[0].split("\"");
                hashresult.add(key[1]);
            }
        }

        return sortKey(new ArrayList<String>(hashresult));
    }

    public ArrayList<String> sortKey(ArrayList<String> list){
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });

        return list;
    }

    public ArrayList<String> getTitleList(ArrayList<String> pathlist){
        ArrayList<String> titlelist = new ArrayList<String>();
        titlelist.add("PROGRAM_KEY");
        for (String s : pathlist){
            String str = s.substring(s.lastIndexOf("values"));
            titlelist.add(str);
        }
        return titlelist;
    }

    public Map<String, Object> combineValue(ArrayList<String> keylist, ArrayList<Map<String, Object>> maplist){
        Map<String, Object> result = new HashMap<String, Object>();

        for (int i=0; i<keylist.size(); i++){
            String key = keylist.get(i);
            ArrayList<Object> values = new ArrayList<Object>();
            for (int j=0; j<maplist.size(); j++) {
                Map<String, Object> map = maplist.get(j);
                String str = "";
                str = (String) map.get(key);
                values.add(str);
            }
            result.put(key, values);
        }

        return result;
    }
}

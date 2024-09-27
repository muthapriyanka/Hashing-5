import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

class Solution {

    int [] indegrees;

    HashMap<Character, HashSet<Character>> map;

    public String alienOrder(String[] words) {

        map = new HashMap<>(); indegrees = new int[26];

        buildGraph(words);

        //bfs

        StringBuilder sb = new StringBuilder(); 

        Queue<Character> q = new LinkedList<>(); 

        for(char c: map.keySet()){

            if(indegrees[c - 'a'] == 0){

                //add the independent char in sb;

                sb.append(c);

                q.add(c); 

            }

        }

        // process the q

        while(!q.isEmpty()){

            char curr = q.poll();

            if(map.get(curr) == null || map.get(curr).size() == 0) continue;

            //iterate on the dependent chars and reduce their indegrees

            for(char in : map.get(curr)){

                indegrees[in - 'a']--;

                // check if the dependent char has becomone independent now 

                if( indegrees[in - 'a'] == 0){

                    q.add(in); 

                    // and append it 

                    sb.append(in);

                }

            }

        }

         /// alt just check if there is any entry > 0 in indegrees array

        if (sb.length() < map.size()) {

            return "";

        }

        return sb.toString(); 

    }

    private void buildGraph(String[] words){

        // iterate over all words and add corresponding character entries in map

        for(String word: words){

            for(char c : word.toCharArray()){

                if(!map.containsKey(c)){

                    map.put(c, new HashSet<>());

                }

            }

        }

        // compare two words at a time to form the node and edge relation

        for(int i = 0; i < words.length - 1; i++){

            String first = words[i]; String second = words[i+1];

            int l1 =first.length(); int l2 = second.length();

             if (l1 > l2 && first.startsWith(second)) {

                 map.clear();

             }

            for(int j = 0; j < l1 && j < l2; j++){

                //at the first differing char

                if(first.charAt(j) != second.charAt(j)){

                    char out = first.charAt(j);

                    char in = second.charAt(j);

                    //if the out char set doesn't have in degree char already

                    if(!map.get(out).contains(in)){

                        map.get(out).add(in); 

                        indegrees[in - 'a']++;

                    }

                    // we needed first differing set of chars only for determining lex order

                    break;

                }

            }

        }

    }

}
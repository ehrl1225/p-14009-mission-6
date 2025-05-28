package com.back.step15.repository;


import com.back.step15.WiseSaying;

/**
 * dynamic sized array to store wise saying
 */
public class WiseSayingList {

    private WiseSaying[] wiseSayings = null;
    private final static int DEFAULT_SIZE = 10;
    private final int WISE_SAYING_NOT_FOUND = -1;
    public final int PAGE_SIZE = 5;
    private int capacity;
    private int size;
    private int lastId = 0;

    WiseSayingList() {
        wiseSayings = new WiseSaying[DEFAULT_SIZE];
        capacity = DEFAULT_SIZE;
        size = 0;
    }

    public void clear(){
        size = 0;
        lastId = 0;
    }

    public int getSize(){
        return size;
    }

    // getter
    public int getLastId(){
        return lastId;
    }

    //setter
    public void setLastId(int id){
        lastId = id;
    }

    /**
     * literally return wise saying index by id
     * @param id
     * @return
     */
    private int getWiseSayingIndexByID(int id){
        int index = WISE_SAYING_NOT_FOUND;
        for (int i = 0; i < size; i++){
            if (wiseSayings[i].getId() == id){
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * literally return wise saying by id
     * @param id
     * @return
     */
    public WiseSaying getWiseSayingById(int id){
        int index = getWiseSayingIndexByID(id);
        if (index == WISE_SAYING_NOT_FOUND){
            return null;
        }
        return wiseSayings[index];
    }


    /**
     * when adding wiseSaying, id is given by this
     * @param wiseSaying
     */
    public void add(WiseSaying wiseSaying) {
        if (size == capacity) {
            WiseSaying[] pre_wiseSayings = wiseSayings;
            // 두 배
            int new_size = capacity << 1;
            wiseSayings = new WiseSaying[new_size];
            System.arraycopy(pre_wiseSayings, 0, wiseSayings, 0, capacity);
            capacity = new_size;
        }
        // must give id to only new made wiseSaying
        if (wiseSaying.getId() == 0){
            wiseSaying.setID(++lastId);
        }
        wiseSayings[size++] = wiseSaying;
    }


    /**
     * make string to show all the items
     * @return
     */
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("번호 / 작가 / 명언\n");
        str.append("----------------------\n");
        for (int i = size -1; i>=0; i--){
            str.append(wiseSayings[i].getId());
            str.append(" / ");
            str.append(wiseSayings[i].getAuthor());
            str.append(" / ");
            str.append(wiseSayings[i].getContent());
            str.append("\n");
        }
        return str.toString();
    }

    /**
     * removes an item of array and shift all items next to it
     * @param id
     * @return shows success or fail on remove
     */
    public boolean removeWiseSayingById(int id){
        int index = getWiseSayingIndexByID(id);
        if (index == WISE_SAYING_NOT_FOUND){
            return false;
        }
        for (int i = index; i< size -1; i++){
            wiseSayings[i] = wiseSayings[i+1];
        }
        wiseSayings[size -1] = null;
        size--;
        return true;
    }



    /**
     * returns iterator
     * @return
     */
    public WiseSayingIterator iterator(){
        return new WiseSayingIterator(size-1, 0, wiseSayings);
    }
    public WiseSayingIterator pagedIterator(int page_num){
        int page_start =  size-1 - (page_num-1)*PAGE_SIZE;
        int page_end = page_start - PAGE_SIZE + 1;
        if (page_end < 0){
            page_end = 0;
        }
        return new WiseSayingIterator(page_start, page_end, wiseSayings);
    }

    /**
     * returning wiseSayings is dangerous because there's null value in there,<br>
     * so I made this not to point null value
     *
     */
    static public class WiseSayingIterator {
        private int end_index;
        private int current_index;
        private WiseSaying[] wiseSayings;

        WiseSayingIterator(int start_index, int end_index, WiseSaying[] wiseSayings){
            this.end_index = end_index;
            current_index = start_index;
            this.wiseSayings = wiseSayings;
        }

        /**
         * this will prevent to point null value
         * @return
         */
        public Boolean hasNext(){
            return current_index > end_index-1 ;
        }

        /**
         * get wise saying but null value
         * @return
         */
        public WiseSaying next(){
            if (!hasNext()){
                return null;
            }
            return wiseSayings[current_index--];
        }

    }
}

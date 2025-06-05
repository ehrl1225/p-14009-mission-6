package com.back.domain.wiseSaying.repository;

import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.simpleDb.SimpleDb;
import com.back.simpleDb.Sql;
import java.util.List;

public class MySqlDatabase {
    SimpleDb simpleDb;

    public MySqlDatabase(SimpleDb simpleDb) {
        this.simpleDb = simpleDb;
    }

    public WiseSaying insert(String content, String author){
        simpleDb.startTransaction();
        Sql sql = simpleDb.genSql();
        sql.append("insert into WiseSaying(content, author) values(?, ?)", content, author);
        long id = sql.insert();
        simpleDb.commit();
        return new WiseSaying((int)id, content, author);
    }

    public boolean delete(int id){
        simpleDb.startTransaction();
        Sql sql = simpleDb.genSql();
        sql.append("delete from WiseSaying where id = ?", id);
        int count = sql.delete();
        simpleDb.commit();
        return count > 0;
    }

    public WiseSaying findById(int id){
        Sql sql = simpleDb.genSql();
        sql.append("select * from WiseSaying where id = ?", id);
        return sql.selectWiseSaying();
    }

    public List<WiseSaying> findAll(){
        Sql sql = simpleDb.genSql();
        sql.append("select * from WiseSaying");
        return sql.selectWiseSayings();
    }

    public List<WiseSaying> findByAuthor(String author){
        Sql sql = simpleDb.genSql();
        sql.append("select * from WiseSaying where author like ?", "%"+author+"%");
        return sql.selectWiseSayings();
    }

    public List<WiseSaying> findByContent(String content){
        Sql sql = simpleDb.genSql();
        sql.append("select * from WiseSaying where content like ?", "%"+content+"%");
        return sql.selectWiseSayings();
    }

    public List<WiseSaying> findByAuthorAndContent(String author, String content){
        Sql sql = simpleDb.genSql();
        sql.append("select * from WiseSaying where author like ? or content like ?", "%"+author+"%", "%"+content+"%");
        return sql.selectWiseSayings();
    }

    public boolean update(WiseSaying wiseSaying){
        simpleDb.startTransaction();
        Sql sql = simpleDb.genSql();
        sql.append("update WiseSaying set content = ?, author = ?", wiseSaying.getContent(), wiseSaying.getAuthor())
                .append("where id = ?", wiseSaying.getId());
        boolean result = sql.update()>0;
        simpleDb.commit();
        return result;
    }
}

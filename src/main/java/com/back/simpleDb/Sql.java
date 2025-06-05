package com.back.simpleDb;

import com.back.Article;
import com.back.domain.wiseSaying.entity.WiseSaying;
import lombok.Setter;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class Sql {
    private final Connection conn;
    private final StringBuilder sql;
    private final  List<Object> args;
    @Setter
    private boolean closeable;


    Sql(Connection conn, Boolean closeable) {
        this.conn = conn;
        sql = new StringBuilder();
        args = new ArrayList<>();
        this.closeable = closeable;

    }

    private void closeConnection(){
        if (closeable) {
            try{
                conn.close();
            }catch (SQLException e){

            }
        }
    }

    public Sql append(String sql, Object... args) {
        this.sql.append(sql + "\n");
        this.args.addAll(Arrays.asList(args));
        return this;
    }

    public Sql appendIn(String sql, Object... args) {
        String[] strs = sql.split("\\?");
        StringBuilder newSql = new StringBuilder();
        newSql.append(strs[0]);
        for (int i = 0; i < args.length-1; i++) {
            newSql.append("?, ");
        }
        newSql.append("?");
        newSql.append(strs[1]);
        newSql.append("\n");

        this.sql.append(newSql);
        this.args.addAll(Arrays.asList(args));
        return this;
    }

    public List<Map<String,Object>> selectRows(){
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql.toString());
            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            List<Map<String,Object>> rows = new ArrayList<>();
            while (rs.next()) {
                Map<String,Object> row = new HashMap<>();
                for (int i = 1; i <= cols; i++) {
                    row.put(rsmd.getColumnName(i), rs.getObject(i));
                }
            }
            closeConnection();
            return rows;

        }catch (SQLException e){

        }
        return null;
    }

    public List<Article> selectRows(Class<Article> t){
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql.toString());
            List<Article> rows = new ArrayList<>();
            while (rs.next()) {
                Article article = getArticle(rs);
                rows.add(article);
            }
            closeConnection();
            return rows;

        }catch (SQLException e){

        }
        return null;
    }

    public List<WiseSaying> selectWiseSayings(){
        try{
            PreparedStatement ps = insertArguments();
            ResultSet rs = ps.executeQuery();
            List<WiseSaying> rows = new ArrayList<>();
            while (rs.next()) {
                WiseSaying wiseSaying = getWiseSaying(rs);
                rows.add(wiseSaying);
            }
            closeConnection();
            return rows;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Map<String,Object> selectRow(){
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql.toString());
            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            Map<String,Object> row = new HashMap<>();
            while (rs.next()) {
                for (int i = 1; i <= cols; i++) {
                    row.put(rsmd.getColumnName(i), rs.getObject(i));
                }
            }
            stmt.close();
            closeConnection();
            return row;
        }catch (SQLException e){

        }
        return null;
    }

    public Article selectRow(Class<Article> t){
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql.toString());
            if (rs.next()) {
                Article article = getArticle(rs);
                stmt.close();
                closeConnection();
                return article;
            }
        }catch (SQLException e){

        }
        return null;
    }

    public WiseSaying selectWiseSaying(){
        try{
            PreparedStatement stmt = insertArguments();
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                WiseSaying wiseSaying = getWiseSaying(rs);
                stmt.close();
                closeConnection();
                return wiseSaying;
            }

        }catch (SQLException e){

        }
        return null;
    }

    public Long selectLong(){
        try{
            PreparedStatement ps = insertArguments();
            ResultSet rs = ps.executeQuery();
            Long result = null;
            if (rs.next()){
                result= rs.getLong(1);
            }
            ps.close();
            closeConnection();
            return result;
        }catch (SQLException e){

        }
        return null;
    }

    public Long insert(){
        try{
            PreparedStatement ps = conn.prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
            int index = 1;
            for (Object arg : args) {
                ps.setObject(index++, arg);
            }
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            Long id = null;
            if (rs.next()) {
                id = rs.getLong(1);
            }
            ps.close();
            closeConnection();
            return id;

        }catch (SQLException e){

        }


        return null;
    }

    public List<Long> selectLongs(){
        try{
            PreparedStatement ps = insertArguments();
            ResultSet rs = ps.executeQuery();
            List<Long> ids = new ArrayList<>();
            while (rs.next()) {
                ids.add(rs.getLong(1));
            }
            closeConnection();
            return ids;

        }catch (SQLException e){

        }
        return null;
    }

    public Boolean selectBoolean(){
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql.toString());
            Boolean result = null;
            if (rs.next()){
                result =  rs.getBoolean(1);
            }
            stmt.close();
            closeConnection();
            return result;
        }catch (SQLException e){

        }
        return null;
    }

    public String selectString(){
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql.toString());
            String result = null;
            if (rs.next()){
                result = rs.getString(1);
            }
            stmt.close();
            closeConnection();
            return result;
        }catch (SQLException e){

        }
        return null;
    }

    public LocalDateTime selectDatetime() {
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql.toString());
            if (rs.next()){
                LocalDateTime date = rs.getTimestamp(1).toLocalDateTime();
                closeConnection();
                return date;
            }
        }catch (SQLException e){

        }
        return null;
    }

    public Integer delete() {
        try{
            PreparedStatement ps = insertArguments();
            Integer result = ps.executeUpdate();
            ps.close();
            closeConnection();
            return result;
        }catch (SQLException e){

        }
        return null;
    }

    public Integer update(){
        try{
            PreparedStatement ps = insertArguments();
            Integer result = ps.executeUpdate();
            ps.close();
            closeConnection();
            return result;

        }catch (SQLException e){
        }
        return null;
    }

    public PreparedStatement insertArguments() throws SQLException{
        PreparedStatement ps = conn.prepareStatement(sql.toString());
        int index = 1;
        for (Object arg : args) {
            ps.setObject(index++, arg);
        }
        return ps;
    }

    public Article getArticle(ResultSet rs) throws SQLException{
        Long id = rs.getLong(1);
        LocalDateTime createdDate = rs.getTimestamp(2).toLocalDateTime();
        LocalDateTime modifiedDate = rs.getTimestamp(3).toLocalDateTime();
        String title = rs.getString(4);
        String body = rs.getString(5);
        Boolean isBlind = rs.getBoolean(6);
        return new Article(id, title, body, createdDate, modifiedDate, isBlind);
    }

    public WiseSaying getWiseSaying(ResultSet rs) throws SQLException{
        int id = rs.getInt(1);
        String content = rs.getString(2);
        String author = rs.getString(3);
        return new WiseSaying(id, content, author);
    }

}

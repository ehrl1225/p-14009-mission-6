package com.back.domain.wiseSaying.controller;

import com.back.AppTest;
import com.back.simpleDb.SimpleDb;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WiseSayingControllerTest {
    static SimpleDb simpleDb;

    public static void createTable() {
        simpleDb.run("drop table if exists WiseSaying");

        simpleDb.run("""
                create table WiseSaying (
                    id int unsigned not null AUTO_INCREMENT,
                    primary key (id),
                    content varchar(255) not null,
                    author varchar(255) not null
                    )
                """);
    }

    public void truncateTable() {
        simpleDb.run("""
                truncate WiseSaying
                """);
    }

    @BeforeAll
    static void init() {
        simpleDb = new SimpleDb("localhost", "root", "lldj123414", "WiseSaying");
        createTable();
    }

    @BeforeEach
    void setUp() {
        truncateTable();
    }

    @Test
    @DisplayName("등록")
    void t2(){
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                종료
                """);
        assertThat(out)
                .contains("명언 : ")
                .contains("작가 : ");
    }

    @Test
    @DisplayName("등록시 명언번호")
    void t3(){
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                종료
                """);
        assertThat(out)
                .contains("1번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("등록시 명언번호 증가")
    void t4(){
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                현재를 사랑하라.
                작자미상
                종료
                """);
        assertThat(out)
                .contains("1번 명언이 등록되었습니다.")
                .contains("2번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("목록")
    void t5(){
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                종료
                """);
        assertThat(out)
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .contains("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("1번 명언삭제")
    void t6(){
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                삭제?id=1
                종료
                """);
        assertThat(out)
                .contains("1번 명언이 삭제되었습니다.")
                .doesNotContain("ID값을 찾지 못했습니다.")
                .doesNotContain("ID가 숫자가 아닙니다.")
                .doesNotContain("1번 명언은 존재하지 않습니다.")
        ;
    }

    @Test
    @DisplayName("명언삭제 예외처리")
    void t7(){
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                삭제?id=1
                삭제?id=1
                종료
                """);
        assertThat(out)
                .contains("1번 명언이 삭제되었습니다.")
                .doesNotContain("ID값을 찾지 못했습니다.")
                .doesNotContain("ID가 숫자가 아닙니다.")
                .contains("1번 명언은 존재하지 않습니다.")
        ;
    }

    @Test
    @DisplayName("명언 수정")
    void t8(){
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                삭제?id=1
                삭제?id=1
                수정?id=3
                수정?id=2
                현재와 자신을 사랑하라.
                홍길동
                목록
                종료
                """);
        assertThat(out)
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .contains("3번 명언은 존재하지 않습니다.")
                .contains("명언(기존) : 과거에 집착하지 마라.")
                .contains("작가(기존) : 작자미상")
                .contains("2 / 홍길동 / 현재와 자신을 사랑하라.")
        ;
    }

    @Test
    @DisplayName("영속성")
    void t9()  {

        final String out1 = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                종료
                """);
        final String out2 = AppTest.run("""
                등록
                현재와 자신을 사랑하라.
                홍길동
                목록
                종료
                """);
        assertThat(out1)
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .contains("1 / 작자미상 / 현재를 사랑하라.")
                .doesNotContain("저장 중 오류가 발생했습니다.")
        ;
        assertThat(out2)
                .contains("3 / 홍길동 / 현재와 자신을 사랑하라.")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .contains("1 / 작자미상 / 현재를 사랑하라.")
                .doesNotContain("저장 중 오류가 발생했습니다.")
        ;
    }

    @Test
    @DisplayName("빌드")
    void t10(){

        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                삭제?id=1
                삭제?id=1
                수정?id=2
                현재와 자신을 사랑하라.
                홍길동
                등록
                현재를 사랑하라.
                작자미상
                목록
                빌드
                종료
                """);

        assertThat(out)
                .contains("data.json 파일의 내용이 갱신되었습니다.")
        ;
    }

    @Test
    @DisplayName("검색")
    void t13(){
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록?keywordType=content&keyword=과거
                수정?id=1
                현재와 자신을 사랑하라.
                홍길동
                목록?keywordType=author&keyword=길동
                종료
                """);

        assertThat(out)
                .contains("----------------------")
                .contains("검색타입 : content")
                .contains("검색어 : 과거")
                .contains("----------------------")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .doesNotContain("1 / 작자미상 / 현재를 사랑하라.")
                .contains("1 / 홍길동 / 현재와 자신을 사랑하라.")
        ;
    }

    @Test
    @DisplayName("페이징")
    void t14(){
        final String out = AppTest.run("""
                등록
                명언 1
                작자미상 1
                등록
                명언 2
                작자미상 2
                등록
                명언 3
                작자미상 3
                등록
                명언 4
                작자미상 4
                등록
                명언 5
                작자미상 5
                등록
                명언 6
                작자미상 6
                등록
                명언 7
                작자미상 7
                등록
                명언 8
                작자미상 8
                등록
                명언 9
                작자미상 9
                등록
                명언 10
                작자미상 10
                목록
                목록?page=2
                종료
                """);
        assertThat(out)
                .contains("페이지 : [1] / 2")
                .contains("페이지 : 1 / [2]")
                .contains("10 / 작자미상 10 / 명언 10")
                .contains("9 / 작자미상 9 / 명언 9")
                .contains("8 / 작자미상 8 / 명언 8")
                .contains("7 / 작자미상 7 / 명언 7")
                .contains("6 / 작자미상 6 / 명언 6")
                .contains("5 / 작자미상 5 / 명언 5")
                .contains("4 / 작자미상 4 / 명언 4")
                .contains("3 / 작자미상 3 / 명언 3")
                .contains("2 / 작자미상 2 / 명언 2")
                .contains("1 / 작자미상 1 / 명언 1")
        ;
    }

}

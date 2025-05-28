package com.back.step15.controller;

import com.back.step15.AppTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class WiseSayingControllerTest {

    @BeforeEach
    void beforeEach(){
        AppTest.clear();
    }

    @Test
    @DisplayName("등록")
    void t1(){
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                종료
                """);

        assertThat(out)
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("목록")
    void t2(){
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
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .contains("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("삭제")
    void t3(){
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
                .contains("1번 명언은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("수정")
    void t4(){
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
                .contains("3번 명언은 존재하지 않습니다.")
                .contains("명언(기존) : 과거에 집착하지 마라.")
                .contains("작가(기존) : 작자미상")
                .contains("2 / 홍길동 / 현재와 자신을 사랑하라.");
    }

    @Test
    @DisplayName("등록")
    void t5(){
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
                목록
                빌드
                종료
                """);
        assertThat(out)
                .contains("data.json 파일의 내용이 갱신되었습니다.");
    }

    @Test
    @DisplayName("목록 검색")
    void t6(){
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록?keywordType=content&keyword=과거
                수정?id=2
                현재와 자신을 사랑하라.
                홍길동
                목록?keywordType=author&keyword=작자
                종료
                """);
        assertThat(out)
                .contains("검색타입 : content")
                .contains("검색어 : 과거")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .contains("검색타입 : author")
                .contains("검색어 : 작자")
                .contains("1 / 작자미상 / 현재를 사랑하라.")
                .doesNotContain("2 / 홍길동 / 현재와 자신을 사랑하라.");
    }

    @Test
    @DisplayName("목록 페이징")
    void t7(){
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
                .contains("페이지 : 1 / [2]");
    }
}

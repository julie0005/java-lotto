package lotto;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

import java.util.List;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomUniqueNumbersInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.*;

class ApplicationTest extends NsTest {
    private static final String ERROR_MESSAGE = "[ERROR]";

    @Test
    void 기능_테스트() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    run("8000", "1,2,3,4,5,6", "7");
                    assertThat(output()).contains(
                            "8개를 구매했습니다.",
                            "[8, 21, 23, 41, 42, 43]",
                            "[3, 5, 11, 16, 32, 38]",
                            "[7, 11, 16, 35, 36, 44]",
                            "[1, 8, 11, 31, 41, 42]",
                            "[13, 14, 16, 38, 42, 45]",
                            "[7, 11, 30, 40, 42, 43]",
                            "[2, 13, 22, 32, 38, 45]",
                            "[1, 3, 5, 14, 22, 45]",
                            "3개 일치 (5,000원) - 1개",
                            "4개 일치 (50,000원) - 0개",
                            "5개 일치 (1,500,000원) - 0개",
                            "5개 일치, 보너스 볼 일치 (30,000,000원) - 0개",
                            "6개 일치 (2,000,000,000원) - 0개",
                            "총 수익률은 62.5%입니다."
                    );
                },
                List.of(8, 21, 23, 41, 42, 43),
                List.of(3, 5, 11, 16, 32, 38),
                List.of(7, 11, 16, 35, 36, 44),
                List.of(1, 8, 11, 31, 41, 42),
                List.of(13, 14, 16, 38, 42, 45),
                List.of(7, 11, 30, 40, 42, 43),
                List.of(2, 13, 22, 32, 38, 45),
                List.of(1, 3, 5, 14, 22, 45)
        );
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() -> {
            runException("1000j");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @Test
    void 사용자_금액_입력_정상_테스트() {
        InputStream in = generateUserInput("8000");
        System.setIn(in);
        int money = IOUtil.moneyInput();
        assertThat(money).isPositive();
        assertThat(money % 1000).isEqualTo(0);
    }

    @Test
    void 사용자_금액_입력_예외_테스트() {
        InputStream in = generateUserInput("1234");
        System.setIn(in);
        assertThatThrownBy(IOUtil::moneyInput).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(ERROR_MESSAGE);
    }

    @Test
    void 당첨번호_입력_정상_테스트() {
        InputStream in = generateUserInput("1,2,3,4,5,6");
        System.setIn(in);
        assertThat(IOUtil.answerNumberInput()).isInstanceOf(Lotto.class);
    }

    @Test
    void 당첨번호_입력_예외_테스트() {
        InputStream in = generateUserInput("132,432$#@");
        System.setIn(in);
        assertThatThrownBy(IOUtil::moneyInput).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(ERROR_MESSAGE);
    }

    public static InputStream generateUserInput(String input) {
        return new ByteArrayInputStream(input.getBytes());
    }
    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}

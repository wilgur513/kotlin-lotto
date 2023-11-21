package lotto.domain

import io.kotest.matchers.throwable.shouldHaveMessage
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.IllegalArgumentException

class ManualLottoDispenserTest {

    @Test
    fun `수동으로 입력한 로또를 발행`() {
        val sut = ManualLottoDispenser(
            createStubLottoGenerator(),
            Lotto(1, 2, 3, 4, 5, 6),
            Lotto(6, 5, 4, 3, 2, 1),
        )

        val actual = sut.issue(2 * LOTTO_PRICE)

        assertThat(actual)
            .contains(
                Lotto(1, 2, 3, 4, 5, 6),
                Lotto(6, 5, 4, 3, 2, 1),
            )
    }

    @Test
    fun `수동으로 입력한 로또를 넘어서면 자동으로 발행`() {
        val sut = ManualLottoDispenser(
            createStubLottoGenerator(Lotto(11, 12, 13, 14, 15, 16)),
            Lotto(1, 2, 3, 4, 5, 6),
            Lotto(6, 5, 4, 3, 2, 1)
        )

        val actual = sut.issue(3 * LOTTO_PRICE)

        assertThat(actual)
            .contains(
                Lotto(1, 2, 3, 4, 5, 6),
                Lotto(6, 5, 4, 3, 2, 1),
                Lotto(11, 12, 13, 14, 15, 16)
            )
    }

    @Test
    fun `수동으로 입력한 로또 갯수가 금액보다 크면 예외 발생`() {
        val sut = ManualLottoDispenser(
            createStubLottoGenerator(Lotto(11, 12, 13, 14, 15, 16)),
            Lotto(1, 2, 3, 4, 5, 6),
            Lotto(6, 5, 4, 3, 2, 1)
        )

        assertThrows<IllegalArgumentException> {
            sut.issue(1 * LOTTO_PRICE)
        }.shouldHaveMessage("수동으로 발급한 로또 금액보다 입력 금액은 크거나 같아야합니다.")
    }

    private fun createStubLottoGenerator(vararg lottos: Lotto): LottoDispenser {
        return LottoDispenser { lottos.toList() }
    }
}

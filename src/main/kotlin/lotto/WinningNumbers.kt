package lotto

class WinningNumbers(private val lottoNumbers: LottoNumbers) {
    fun calculateRank(lottoNumbers: LottoNumbers): Rank {
        val intersection = this.lottoNumbers.numbers.intersect(lottoNumbers.numbers)
        val matchedCount = intersection.size

        return Rank.from(matchedCount)
    }
}
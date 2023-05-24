/**
 * 文字省略機能
 */
function truncateCardImageText() {
    const cardImages = document.getElementsByClassName('cardlist-image');
    const maxChars = 10; // 最大表示文字数

    for (let i = 0; i < cardImages.length; i++) {
        const cardImageElement = cardImages[i];
        const cardImageText = cardImageElement.textContent;

        if (cardImageText.length > maxChars) {
            const truncatedText = '...' + cardImageText.substring(cardImageText.length - maxChars);
            cardImageElement.textContent = truncatedText;
            cardImageElement.title = cardImageText; // ツールチップとして全文を表示
        }
    }
}

function updateBookmark(checkbox) {
    var checked = checkbox.checked;
    var id = checkbox.closest('.business-card').getAttribute('data-id');
    var url = "/businesscard/viewList/" + id + "?checked=" + checked;
  

    // 以下の処理は変更せずにそのまま使用できます
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': document.getElementById('csrfToken').value
        }
    })
        .then(function(response) {
            if (response.ok) {
                // ブックマークの状態を更新した後の処理
                if (checked) {
                    console.log("ブックマークをオンにしました。");
                } else {
                    console.log("ブックマークをオフにしました。");
                }
            } else {
                // エラーハンドリング
                console.error("エラーが発生しました。");
            }
        })
        .catch(function(error) {
            // エラーハンドリング
            console.error("エラーが発生しました。", error);
        });
}

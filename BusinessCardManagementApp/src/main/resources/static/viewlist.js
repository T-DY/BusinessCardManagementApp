/** スクロールに表現を付け加える */
function loadMoreContent() {
    // Ajaxリクエストを作成して新しいコンテンツを取得します
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/load-more', true);
    xhr.onreadystatechange = function() {
      if (xhr.readyState === 4 && xhr.status === 200) {
        // 取得したコンテンツをDOMに追加します
        var newContent = xhr.responseText;
        document.getElementById('content-container').innerHTML += newContent;
        
        // スクロール位置を保存
        var previousScrollPosition = window.pageYOffset;
    
        // スクロール位置を元に戻す
        window.scrollTo(0, previousScrollPosition);
      }
    };
    xhr.send();
  }
  
  window.addEventListener('scroll', function() {
    var scrollPosition = window.pageYOffset;
    var windowSize = window.innerHeight;
    var bodyHeight = document.body.offsetHeight;
  
    if (scrollPosition + windowSize >= bodyHeight) {
      loadMoreContent();
    }
  });
  
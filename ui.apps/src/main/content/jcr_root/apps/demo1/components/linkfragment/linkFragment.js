document.addEventListener('DOMContentLoaded', function() {
    var linkText = document.getElementById('linkContainer').innerText;

    if (linkText && (linkText.startsWith('http://') || linkText.startsWith('https://'))) {
        var anchorTag = document.createElement('a');
        anchorTag.href = linkText;
        anchorTag.target = '_blank'; // Open in a new tab
        anchorTag.innerText = linkText;

        var container = document.getElementById('linkContainer');
        container.innerHTML = ''; // Clear the div
        container.appendChild(anchorTag); // Add the anchor tag
    }
});

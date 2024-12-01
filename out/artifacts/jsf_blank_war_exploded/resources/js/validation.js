document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("editForm");
    const keywordsContainer = document.getElementById("keywords-container");
    const newKeywordInput = document.getElementById("new-keyword");

    form.addEventListener("submit", function(event) {
        const messages = document.querySelectorAll(".ui-messages-error li");
        const keywords = keywordsContainer.querySelectorAll("li span");

        let errorMessage = "Please correct the following errors:\n";
        let hasError = false;

        if (messages.length > 0) {
            event.preventDefault();
            messages.forEach(function(message) {
                errorMessage += "- " + message.textContent + "\n";
                hasError = true;
            });
        }

        // Check if there is at least one keyword
        if (keywords.length === 0) {
            event.preventDefault();
            errorMessage += "- At least one keyword is required.\n";
            hasError = true;
        }

        if (errorMessage !== "Please correct the following errors:\n") {
            alert(errorMessage);
        }
    });

    // Prevent adding empty or duplicate keywords
    document.getElementById("addKeywordButton").addEventListener("click", function(event) {
        const newKeyword = newKeywordInput.value.trim();
        const keywords = Array.from(keywordsContainer.querySelectorAll("li span")).map(span => span.textContent);

        if (newKeyword === "") {
            event.preventDefault();
            alert("Keyword cannot be empty.");
        } else if (keywords.includes(newKeyword)) {
            event.preventDefault();
            alert("Duplicate keyword cannot be added.");
        }
    });

    // Prevent deleting the last keyword
    keywordsContainer.addEventListener("click", function(event) {
        if (event.target.classList.contains("remove-keyword")) {
            const keywords = keywordsContainer.querySelectorAll("li span");
            if (keywords.length <= 1) {
                event.preventDefault();
                alert("At least one keyword is required.");
            }
        }
    });
});
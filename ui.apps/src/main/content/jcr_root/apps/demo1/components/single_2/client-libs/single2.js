document.addEventListener("DOMContentLoaded", function() {
    const dateInput = document.getElementById("datePicker");
    const output = document.getElementById("output");

    if (dateInput) {
        dateInput.addEventListener("change", function() {
            const selectedDate = new Date(dateInput.value);
            const today = new Date();

            // Remove time from today and selected date for comparison
            today.setHours(0, 0, 0, 0);
            selectedDate.setHours(0, 0, 0, 0);

            // Show or hide content based on selected date
            if (selectedDate >= today) {
                output.innerHTML = `
                    <p><strong>Text:</strong> ${document.getElementById("textField").value}</p>
                    <p><strong>Path:</strong> ${document.getElementById("pathField").value}</p>
                `;
            } else {
                output.innerHTML = <p>Component Expired</p>;
            }
        });
    }
});
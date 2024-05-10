document.addEventListener('DOMContentLoaded', function() {
    const sendRequestButtons = document.querySelectorAll('.btn-delete');
    // Iterate over each button and add the event listener
    sendRequestButtons.forEach(function(sendRequestButton) {
        sendRequestButton.addEventListener('click', function(event) {
            event.preventDefault(); // Prevent the default form submission behavior
            const link = this.getAttribute('href'); // Get the itemId attribute value
            document.getElementById('btnConfirm').setAttribute('href', link);
          });
    });
});
$(document).ready(function () {
    $('.quantity').change(function () {
        let quantity = $(this).val();
        let id = $(this).attr('data-id');
        $.ajax({
            url: '/books/update-cart',
            type: 'POST',
            data: { id: id, quantity: quantity },
            success: function () {
                location.reload();
            }
        });
    });
});
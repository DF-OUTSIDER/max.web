$(function () {
    $('#selectAll').change(function () {
        var checked = this.checked;
        $('#operationList tbody tr td:first-child').each(function (i, e) {
            $(e).find('input[type="checkbox"]').prop('checked', checked);
        });
    });

    $('#delBtn').click(function () {
        var deletedId = [];
        $('#operationList tbody tr').each(function (i, e) {
            var chkBox = $(e).find('td:eq(0)').find('input[type = "checkbox"]');
            if ($(chkBox).prop('checked')) {
                deletedId.push(parseInt(chkBox.val()));
            }
        });

        $.post($('#delUrl').attr('href'),
            {
                ids: deletedId
            },
            function (e) {
                if (e.success) {
                    window.href.reload();
                }
            });
    });
});

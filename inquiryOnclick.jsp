<script>
    function inquiry() {
    
        $(function() {
            $("li.inquiryCenter > a").click(function(e) {
                let userNo = "${loginUser.userNo}";
                if(userNo = 1) {
                let url = "all.in?userNo=" + userNo;
                location.href = url;
                } else {
                let url = "list.in?userNo=" + userNo;
                location.href = url;
                }
            });
        });
    }
</script>

<script>
    function inquiry() {
        $(function() {
            $("li.inquiryCenter > a").click(function(e) {
                let userNo = "${loginUser.userNo}";
                let url = "list.in?userNo=" + userNo;
                location.href = url;
            });
        });
    }
</script>
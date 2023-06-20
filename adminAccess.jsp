<c:if test="${ (not empty loginUser) and (loginUser.userName eq '관리자') }">
    <div align="right">
            <a class="btn btn-danger" onclick="postFormSubmit();">삭제하기</a>
    </div>
    <br>

    <form id="postForm" action="" method="post">
        <input type="hidden" name="inquiryNo" value="${ i.inquiryNo }">
    </form>
    <script>
        function postFormSubmit() {
                $("#postForm").attr("action", "delete.no").submit();
        }
    </script>
</c:if>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:requestEncoding value="utf-8"/>

<form name="frmForm" id="_frmForm" method="post" action="infoupdate.do">

<input type="hidden" name="seq" value="${info.seq}"/>

<table >

<colgroup>
<col style="width:200px;" />
<col style="width:auto;" />
</colgroup>

<tbody>	
	<tr class="id">
		<th>아이디</th>
		<td style="text-align: left">${info.id}</td>
	</tr>
	<tr>
		<th>제목</th>
		<td style="text-align: left">${info.title}</td>
	</tr>
	<tr>
		<th>작성일</th>
		<td style="text-align: left">${info.wdate}</td>
	</tr>
	<tr>
		<th>내용</th>
		<td style="text-align: left"><textarea rows="10" cols="50" 
		name='content' id="_content">${info.content}</textarea></td>
	</tr>
	<tr>
		<td colspan="2" style="height:50px; text-align:center;">
		<span>
			<%-- <c:if test="${info.id eq login.id}"> --%>
			<i class="far fa-edit" id="_btnUpdate">수정</i>
			<i class="fas fa-trash-alt" id="_btnDel">삭제</i>
			<%-- </c:if> --%>
			<i class="fas fa-reply" id="_btnReply">답글</i>
		</span>
		</td>
	</tr>
</tbody>
</table>

</form>


<script type="text/javascript">
$("#_btnUpdate").click(function() {	
	$("#_frmForm").attr({ "target":"_self", "action":"infoupdate.do" }).submit();
});

$("#_btnDel").click(function() {			
	$("#_frmForm").attr({ "target":"_self", "action":"infodelete.do" }).submit();
});
</script>






<!----- 댓글 시작  ----->

<i class="fas fa-reply" id="btnRe">댓글 작성</i>


<form name="ReForm" id="ReForm" method="post" action="">
<textarea rows="2" cols="30" name="content"></textarea>
<input type="hidden" name="info_Seq" value="${info.seq}"/>
<%-- <input type="hidden" name="id" value="${login.id}"/> --%>
<input type="hidden" name="id" value="aaa"/>
	<table border="1">
		<colgroup>
			<col style="width:70px">
			<col style="width:100px">
			<col style="width:70px">
		</colgroup>

		<thead>
			<tr>
				<th>아이디</th><th>내용</th><th>작성일</th>
			</tr>
		</thead>

		<tbody>
			<c:if test="${empty infoReList }">
			<tr>
				<td colspan="3">작성된 댓글이 없습니다</td>		
			</tr>	
			</c:if>
		
		
			<c:forEach items="${infoReList }" var="infoRe" varStatus="Revs">
			
				<!-- arrow를 setting -->
				<%-- <jsp:setProperty property="depth" name="ubbs" value="${bbs.depth }"/> --%>
				
				<tr>
					<td>${infoRe.id}</td>
					
					<c:if test="${infoRe.del eq 1}">
						<td>[삭제된 댓글 입니다.]</td>
					</c:if>
					
			 		<c:if test="${infoRe.del eq 0}">
						<td>
							<%-- <jsp:getProperty property="arrow" name="ubbs"/> --%>
							<%-- <a href="infodetail.do?seq=${info.seq }">
								${info.title}
							</a> --%>
							${infoRe.content}
						</td>
					</c:if>			
					
					<td>${infoRe.wdate}</td>
				</tr>		
			</c:forEach>
		</tbody>
	</table>
</form>
<script type="text/javascript">

$("#btnRe").click(function() {	
	alert('답글달기');	
	$("#ReForm").attr({ "target":"_self", "action":"writeRe.do" }).submit();
});

</script>



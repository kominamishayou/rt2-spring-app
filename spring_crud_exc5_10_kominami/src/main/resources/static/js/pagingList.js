document.addEventListener("DOMContentLoaded", () => {
	const params = new URLSearchParams(window.location.search);
	const empName = params.get("empName") || "";
	const deptId = Number(params.get("deptId")) || 1;
	const pageSize = 10;

	function loadPage(page) {
		$.ajax({
			url: FRAGMENT_URL,
			method: "GET",
			data: { 
				page: page,
			 	size: pageSize, 
			 	searchType: searchType,
			 	empName: empName,
			 	deptId: deptId },
			success: function(html) {
				$("#employee-list").replaceWith(html);
			},
			error: function() {
				alert("読み込みに失敗しました" + page);
			}
		});

	}


	$(document).on("click", ".page-link", function() {
		const page = $(this).data("page");
		if (page === undefined) return;
		loadPage(page);
	});
})
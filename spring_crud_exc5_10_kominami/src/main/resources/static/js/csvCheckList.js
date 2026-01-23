document.addEventListener("DOMContentLoaded", () => {

	function setupPager(containerSelector, size) {
		const pageSize = size;

		const container = $(containerSelector);
		const rows = container.find(".list_table tr").not(":first");
		const total = rows.length;
		const totalPages = Math.max(1, Math.ceil(total / pageSize));

		let currentPage = 1;

		function showPage(page) {
			rows.hide();

			const start = (page - 1) * pageSize;
			const end = start + pageSize;

			rows.slice(start, end).show();

			container.find(".csv-pager-countor-current").text(page);
			container.find(".csv-pager-countor-total").text(totalPages);

			// ボタンの無効化（任意）
			container.find(".csv-page-link-prev").prop("disabled", page <= 1);
			container.find(".csv-page-link-next").prop("disabled", page >= totalPages);
		}

		container.find(".csv-page-link-prev").on("click", function() {
			if (currentPage > 1) {
				currentPage--;
				showPage(currentPage);
			}
		});

		container.find(".csv-page-link-next").on("click", function() {
			if (currentPage < totalPages) {
				currentPage++;
				showPage(currentPage);
			}
		});

		showPage(currentPage);
	}

	// ページャーを両方に設定
	setupPager(".valid-table", 5);
	setupPager(".invalid-table", 2);


	const validTable = $(".valid-table");
	const invalidTable = $(".invalid-table");


	function showValidTable() {
		invalidTable.hide();
		validTable.show();
	};

	function showInalidTable() {
		validTable.hide();
		invalidTable.show();
	};

	$(".show-valid-tab").on("click", showValidTable);
	$(".show-invalid-tab").on("click", showInalidTable);


	//nullセルの背景色を変える
	const $invalidTable = $(".invalid-table");
	const $checkTdList = $invalidTable.find(".check-td");

	const checkTd = function() {
		const $td = $(this)

		if ($td.text().trim() === '') {
			$td.css('background-color', 'red');
			$td.css('opacity', '0.25');
		}
	};

	const allCheckTd = function() {
		$checkTdList.each(checkTd);
	}

	allCheckTd();
});
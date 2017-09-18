$(function() {
	$.post('yourReimbursementsServlet',
			{},
			function(arr) {
				let table = $('#reimbursementsTable').DataTable({
				        createdRow: function() {
				            this.api().columns().every(function() {
				                let column = this;
				                let select = $('<select><option value=""></option></select>')
				                    .appendTo($(column.footer()).empty())
				                    .change(function() {
				                        let val = $.fn.dataTable.util.escapeRegex($(this).val());
				                        column.search(val ? '^'+val+'$' : '', true, false).draw();
				                    });
				                column.data().unique().sort().each(function(d, j) {
				                    select.append('<option value="'+d+'">'+d+'</option>');
				                });
				            });
				        },
				        rowCallback: function(row, data, index) {
				        	switch (data[2]) {
				        	case "pending": $(row).find('td:eq(2)').css('color', 'yellow');
				        			break;
				        	case "approved": $(row).find('td:eq(2)').css('color', 'green');
				        			break;
				        	case "denied": $(row).find('td:eq(2)').css('color', 'red');
				        	}
				        }
				    });
				for (let i=0; i<arr.length; i++) {
					console.log(arr[i]);
					let resolverTxt;
					let resolutionTxt;
					if (arr[i].status === 'pending') {
						resolverTxt = 'unresolved';
						resolutionTxt = 'unresolved';
					} else {
						resolverTxt = arr[i].resolver.first + " " + arr[i].resolver.last;
						resolutionTxt = arr[i].resolutionDate;
					}
					table.row.add([
					        arr[i].description,
					        '$'+arr[i].amount,
					        arr[i].status,
					        resolverTxt,
					        arr[i].submissionDate,
					        resolutionTxt
					               ]).draw();
				}
			});
	
	$('#logoutBtn').click(function() {
		$.post('logoutServlet',
				{},
				function(data) {
					window.location.replace('login.html');
				});
	});
	
	$('#accInfoBtn').click(function() {
		window.location.replace('accInfo.html');
	});
	
	$('#newReimbursementBtn').click(function() {
		window.location.replace('newReimbursement.html');
	});
	
});
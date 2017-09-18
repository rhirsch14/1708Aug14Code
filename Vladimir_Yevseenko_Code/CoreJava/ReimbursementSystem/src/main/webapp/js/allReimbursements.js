$(function() {
	$.post('allReimbursementsServlet',
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
				        	switch (data[3]) {
				        	case "pending": $(row).find('td:eq(3)').css('color', 'yellow');
				        			break;
				        	case "approved": $(row).find('td:eq(3)').css('color', 'green');
				        			break;
				        	case "denied": $(row).find('td:eq(3)').css('color', 'red');
				        	}
				        }
				    });
				for (let i=0; i<arr.length; i++) {
					console.log(arr[i]);
					let resolverTxt, submitterTxt, resolutionTxt;
					if (arr[i].status === 'pending') {
						resolverTxt = 'unresolved';
						resolutionTxt = 'unresolved';
					} else {
						resolverTxt = arr[i].resolver.first + " " + arr[i].resolver.last;
						resolutionTxt = arr[i].resolutionDate;
					}
					table.row.add([
					        arr[i].id,
					        arr[i].description,
					        '$'+arr[i].amount,
					        arr[i].status,
					        arr[i].submitter.first + " " + arr[i].submitter.last,
					        resolverTxt,
					        arr[i].submissionDate,
					        resolutionTxt
					               ]).draw(false);
				}
			},
			'JSON');
	
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
	
	$('#registerEmployeeBtn').click(function() {
		window.location.replace('registerEmployee.html');
	});
	
	$('#viewEmployeesBtn').click(function() {
		window.location.replace('employees.html');
	});
	
	$('#openModal').click(function() {
		$('#modal').css('display', 'block');
	});
	
	$('#closeModal').click(function() {
		$('#modal').css('display', 'none');
	});
	
	$('#resolveBtn').click(function() {
		
		$('#reimbIdIn').css('border-color', 'black');
		$('#message').text('');
		
		if (!validateId($('#reimbIdIn').val())) {
			$('#reimbIdIn').css('border-color', 'red');
			$('#message').text('Please enter a valid id number');
			$('#message').css('color', 'red');
		} else {
			$.post('resolutionServlet',
					{ id: $('#reimbIdIn').val(), approved: $('#status').val() },
					function(response) {
						if (response.success === 'success') {
							$('#message').text('Reimbursement request successfuly ' + $('#status').val());
							$('#message').css('color', 'green');
							location.reload();
						} else {
							$('#reimbIdIn').css('border-color', 'red');
							$('#message').css('color', 'red');
							if (response.success === 'notPending')
								$('#message').text('Reimbursement request is not pending')
							else
								$('#message').text('Reimbursement request does not exist')
						}
						
					},
					'JSON');
		}
		setTimeout(function() {
			$('#reimbIdIn').css('border-color', 'black');
			$('#message').text('');
		}, 3000);
	});
});

function validateId(id) {
	var regex = /^([1-9]\d*)$/;
	return regex.test(id);
}
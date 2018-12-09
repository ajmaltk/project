$(function() {

	$('#datetimepicker3').datetimepicker();

	$('#task').submit(
			function(e) {
				$("#errorTaskDiv").slideUp(400);
				e.preventDefault();
				$.ajax({
					type : "POST",
					headers : {
						Accept : "application/json",

					},
					url : "/AssignTask/testTask",
					data : $('form').serialize(),
					success : function(result) {
						
						if (result.status == "success") {

							$(':input', '#task').not(
									':button, :submit, :reset, :hidden')
									.val('').prop('checked', false).prop(
											'selected', false);
							$('#modalTestForm').modal('hide').on(
									'hidden.bs.modal',
									function(e) {

										$('#testTableModal').modal('show');
										$('#idTaskStartTime').html(
												result.taskStartTime);
										$('#idTaskDuration').html(
												result.duration);
										$('#idTaskEndTime').html(
												result.taskEndTime);
										$(this).off('hidden.bs.modal');
									});
						} else if(result.status =="NoObject") {
							alert("ddsfs");
							
							$("#errorTaskDiv").slideDown(400);
							$('#errorTaskDiv').html(result.message);

						}else{
							
							
							$("#errorTaskDiv").slideDown(400);
							$('#errorTaskDiv').html(result.message);
						}

					}
				});

			});

	$('#schedule')
			.submit(
					function(e) {

						$("#errorDiv").slideUp(400);
						$("#successDiv").slideUp(400)
						e.preventDefault(); // don't submit multiple times

						$.ajax({
									type : "POST",
									headers : {
										Accept : "application/json",

									},
									url : "/AssignTask/submit",
									data : $('form').serialize(),
									success : function(result) {

										if (result.status == "success") {

											$("#successDiv").slideDown(400);

											$('#successDiv')
													.html(
															result.message
																	+ '<a class="close" data-dismiss="alert" >x</a>');

											setTimeout(function() {
												$("#successDiv").slideUp(400);
											}, 4000);

											$(':input', '#schedule')
													.not(
															':button, :submit, :reset, :hidden, :checkbox ')
													.val('');
											$('input:checkbox').removeAttr(
													'checked');
											
										} else {

											$("#errorDiv").slideDown(400);
											$('#errorDiv').html(result.message);

											

										}

									
									}
								});

					});

});

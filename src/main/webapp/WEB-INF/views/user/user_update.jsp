<%@ include file="../layouts/header.jsp" %>
<%@ include file="../layouts/sidenav.jsp"%>
<div class="main_contents">
    <div id="sub_content">
        <% User log_user = (User) session.getAttribute("valid_user");%>
        <form method="post" action="userUpdate">

            <h2 class="col-md-6 offset-md-2 mb-5 mt-4">User Update</h2>
             <h2 class="col-md-6 offset-md-2 mb-5 mt-4"></h2>
            <% User c_user = (User) request.getAttribute("user");%>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="name" class="col-md-2 col-form-label">ID</label>
                <div class="col-md-4">
                    <input type="text" value="<%=String.format("USR%03d",c_user.getId())%>" class="form-control" name="id" id="id" disabled>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="name" class="col-md-2 col-form-label">Name</label>
                <div class="col-md-4">
                    <input type="text" value="<%=c_user.getName()%>" class="form-control" name="name" id="name">
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="email" class="col-md-2 col-form-label">Email</label>
                <div class="col-md-4">
                    <input type="email" value="<%=c_user.getEmail()%>" class="form-control" name="email" disabled id="email">
                    <% if(log_user.getEmail().equalsIgnoreCase(c_user.getEmail())) { %>
                    <button style="margin-top: 20px" type="button" class="btn btn-dark" onclick="changeEmail()">Change Email</button>
               <% } %>
                </div>
            </div>
            <input type="hidden" name="id"/>
            
            <div class="row mb-4">
                <div class="col-md-4"></div>

                <div class="col-md-6">

                    <button type="submit" class="btn btn-success col-md-2" data-bs-toggle="modal" data-bs-target="#exampleModal">Update</button>
                    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                        aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">User Update</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                                </div>
                                <div class="modal-body">

                                    <h5 style="color: rgb(127, 209, 131);">Successfully Updated !</h5>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-success col-md-2" data-bs-dismiss="modal">Ok</button>

                                </div>
                            </div>
                        </div>
                    </div>
                    <a href="userList" class="btn btn-secondary col-md-2" >Back</a>

                </div>
            </div>
        </form>
    </div>
</div>

<script>
    function changeEmail() {
        window.location.href = 'changePassword';
    }
</script>

<%@ include file="../layouts/footer.jsp"%>

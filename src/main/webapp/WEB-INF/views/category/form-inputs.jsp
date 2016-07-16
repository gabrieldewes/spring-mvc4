<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  	        <div class="form-group">
          <label for="name">nome</label>
          <div class="input-group">
            <span class="input-group-addon"><i class=""></i></span><form:input path='name' type='text'/>
<form:errors path='name'/>

          </div>
        </div>
        <div class="form-group">
          <label for="description">description</label>
          <div class="input-group">
            <span class="input-group-addon"><i class=""></i></span><form:input path='description' type='text'/>
<form:errors path='description'/>

          </div>
        </div>

<%@ page import="com.futurenowsoftwaredesign.picturethis.Image" %>

<tr>
    <td>
        <div class="fieldcontain ${hasErrors(bean: imageInstance, field: 'picture', 'error')} required">
            <g:if test="${imageInstance?.picture == null}">
                <label for="picture">
                    <g:message code="image.picture.label" default="Picture"/>
                    <span class="required-indicator">*</span>
                </label>
                <input type="file" id="picture" name="picture"/>
            </g:if>
            <g:else>
                <img src="<g:createLink controller='image' action='renderImage' id='${imageInstance.id}'/>" height="140"
                     width="140"/>
            </g:else>
        </div>
    </td>

    <td>
         <div class="fieldcontain ${hasErrors(bean: imageInstance, field: 'caption', 'error')} required">
             <label for="caption">
                 <g:message code="image.caption.label" default="Caption" />
                 <span class="required-indicator">*</span>
             </label>
             <g:textField name="caption" maxlength="40" required="" value="${imageInstance?.caption}"/>
         </div>

         <div class="fieldcontain ${hasErrors(bean: imageInstance, field: 'description', 'error')} ">
             <label for="description">
                 <g:message code="image.description.label" default="Description" />

             </label>
             <g:textField name="description" value="${imageInstance?.description}"/>
         </div>

     </td>
 </tr>





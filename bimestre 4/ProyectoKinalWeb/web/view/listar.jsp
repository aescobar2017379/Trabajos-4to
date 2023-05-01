<%-- 
    Document   : listar
    Created on : 25/08/2022, 02:54:33 PM
    Author     : angel
--%>

<%@page import="model.Persona"%>
<%@page import="java.util.List"%>
<%@page import="modelDAO.PersonaDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listar Personas</title>
    </head>
    <body>
        <h1>Registro de Personas</h1>
        <table border="1">
            <thead>
                <tr>
                    <th>CODIGO PERSONA</th>
                    <th>DPI</th>
                    <th>NOMBRES</th>
                    <th>ACCIONES</th>
                </tr>
            </thead>
            <%
                PersonaDAO dao = new PersonaDAO();
                List<Persona> listaPersona = dao.listar();
             %>
            <tbody>
                <tr>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                </tr>
            </tbody>
            
        </table>
    </body>
</html>

Attribute VB_Name = "UtilityFunctions"
Option Explicit


Private Const TABLE_WIDTH As Double = 2
Private Const TABLE_HEIGHT As Double = 0.3


Public Sub Test()
    Dim pg As Visio.Page
    Set pg = Visio.ActivePage
    
    
    
    
    ' Add shapes
    Dim table1 As Visio.Shape
    Dim table2 As Visio.Shape
    Dim table3 As Visio.Shape
    Set table1 = pg.Shapes.ItemFromUniqueID(addTable(pg, "Table1"))
    Set table2 = pg.Shapes.ItemFromUniqueID(addTable(pg, "Table2"))
    Set table3 = pg.Shapes.ItemFromUniqueID(addTable(pg, "Table3"))
    
    
    ' Set location
    setShapeCoordinates table1, 10, 10
    setShapeCoordinates table2, 10, 5
    setShapeCoordinates table3, 15, 5
    
    
    ' Connect shapes
    autoConShapes pg, table1, table2
    autoConShapes pg, table1, table3
    
End Sub
Public Sub setShapeCoordinates(ByRef shapeObj As Visio.Shape, ByVal coord_x As Double, ByVal coord_y As Double)
    shapeObj.Cells("pinx").Result("inches") = coord_x
    shapeObj.Cells("piny").Result("inches") = coord_y
End Sub
Public Sub autoConShapes(ByRef active_page As Page, ByRef shape_1 As Visio.Shape, ByRef shape_2 As Visio.Shape)
' Description: - Autoconnects two shapes with a line (no arrows)
'              - Uses temporary rectangle to connect the two shapes

    
    ' Build temporary connector shapes
    Dim tempConnectorLine As Visio.Shape
    Set tempConnectorLine = active_page.DrawLine(2, 2, 3, 3)
    
    
    ' Autoconnect shapes with temporary connector shape; temp connector shape still exists after connecting
    shape_1.AutoConnect shape_2, visAutoConnectDirNone, tempConnectorLine
    
    
    ' Convert connector line to straight
    convertToStraightConnect getNewShapeId(active_page)
    
    
    ' Delete temporary connector shape
    tempConnectorLine.Delete
End Sub
Public Function addTable(active_page As Page, tableName As String) As String
    
    ' Create new table
    Dim newTable As Visio.Shape
    Set newTable = active_page.DrawRectangle(1, 1, 5, 5)
    
    
    ' Return new table UID
    ' 0 - Returns shape UID   1 - Creates and returns new UID if not already existing
    addTable = newTable.UniqueID(1)
    
    
    ' Set table width and height
    newTable.Cells("height") = TABLE_HEIGHT
    newTable.Cells("width") = TABLE_WIDTH
    
    
    ' Set table name
    newTable.Text = tableName
End Function
Sub convertToStraightConnect(conn_shape_id As Integer)

    'Enable diagram services
    Dim DiagramServices As Integer
    DiagramServices = ActiveDocument.DiagramServicesEnabled
    ActiveDocument.DiagramServicesEnabled = visServiceVersion140 + visServiceVersion150

    Dim UndoScopeID1 As Long
    UndoScopeID1 = Application.BeginUndoScope("Straight Connector")
    Application.ActiveWindow.Page.Shapes.ItemFromID(conn_shape_id).CellsSRC(visSectionObject, visRowShapeLayout, visSLOLineRouteExt).FormulaU = "1"
    Application.ActiveWindow.Page.Shapes.ItemFromID(conn_shape_id).CellsSRC(visSectionObject, visRowShapeLayout, visSLORouteStyle).FormulaU = "16"
    Application.EndUndoScope UndoScopeID1, True

    'Restore diagram services
    ActiveDocument.DiagramServicesEnabled = DiagramServices

End Sub
Public Function getNewShapeId(active_page As Page) As Integer
    
    ' Ensure active page has any shapes
    If Not active_page.Shapes.Count > 0 Then
        getNewShapeId = 0
        Exit Function
    End If
    
    
    ' Iterate through all shapes
    Dim s As Visio.Shape
    Dim lastId As Integer
    For Each s In active_page.Shapes
        lastId = s.ID
    Next s
    
    
    ' Return last shape ID
    getNewShapeId = lastId
End Function
Public Function getNewShapeUid(active_page As Page) As String

    ' Ensure active page has any shapes
    If Not active_page.Shapes.Count > 0 Then
        getNewShapeUid = ""
        Exit Function
    End If
    
    ' Iterate through all shapes
    Dim s As Visio.Shape
    Dim lastUid As String
    For Each s In active_page.Shapes
        lastUid = s.ID
    Next s
    
    
    ' Return last shape UID
    getNewShapeId = lastUid
End Function
Public Function addMakeTable(ByRef active_page As Page, mt_name As String) As String
    ' Ensure Basic Shapes stencil document exists
    addStencil "basic_u.vss"
    
    
    ' Retrieve Rounded Rectangle Master object
    Dim roundedRec As Master
    Set roundedRec = Application.Documents("Stencil6").Masters("Rounded Rectangle")
    
    
    ' Drop the rounded rectangle onto the active page
'    Dim newMakeTable As Shape
    active_page.Drop roundedRec, 3.5, 3.5
    
    
    ' Set make-table name
'    newMakeTable.Name = mt_name
    
    
    ' Return newly created make-table UID
'    addMakeTable = newMakeTable.UniqueID(1)
    
End Function
Function addStencil(stencilName As String)
    
    
    ' Search for specified document item
    Dim docItem As Document
    Dim pathList As Variant
    For Each docItem In Application.Documents
        Debug.Print docItem.name
        If InStr(1, LCase(docItem.name), "stencil") > 0 Then
            Exit Function
        End If
    Next docItem
    
    
    ' Add the doument item if it wasn't already found
    Application.Documents.Add stencilName
    
    
End Function














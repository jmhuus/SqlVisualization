VERSION 1.0 CLASS
BEGIN
  MultiUse = -1  'True
END
Attribute VB_Name = "Node"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = False
Attribute VB_Exposed = False
Option Explicit

Private pg As Visio.Page
Private nodeTitle As String
Private nodeUid As String
Private nodePosition As Variant
Private nodeType As String
Private nodeChildren As Collection
Private nodeShapeObj As Visio.Shape



Public Sub constructor(active_page As Visio.Page, nTitle As String, nPosition As Variant, nType As String, Optional nChildren As Collection)

    Set pg = active_page
    nodeTitle = nTitle
    nodePosition = nPosition
    nodeType = nType
    If Not nChildren Is Nothing Then
        Set nodeChildren = nChildren
    Else
        Set nodeChildren = New Collection
    End If
    
    
    ' Draw node on page
    nodeUid = UtilityFunctions.addTable(pg, nTitle)
    Set nodeShapeObj = pg.Shapes.ItemFromUniqueID(nodeUid)
    
    
    ' Relocate shape
    UtilityFunctions.setShapeCoordinates nodeShapeObj, nodePosition(0), nodePosition(1)
End Sub



Public Sub addChild(newNode As Node)
    nodeChildren.Add newNode
End Sub
Public Sub updatePosition(nPosition As Variant)
    nodePosition = nPosition
    UtilityFunctions.setShapeCoordinates nodeShapeObj, nodePosition(0), nodePosition(1)
End Sub
Public Sub connectChildren()
    Dim nodeChild As Node
    For Each nodeChild In nodeChildren
        ' Connect children to current node
        UtilityFunctions.autoConShapes pg, nodeShapeObj, nodeChild.getShapeObj
        
        
        ' Recursion; each child to begin connecting it's children
        nodeChild.connectChildren
    Next nodeChild
End Sub



' Getters/Setters
Public Function getShapeObj() As Visio.Shape
    Set getShapeObj = nodeShapeObj
End Function
Public Function getChildren() As Collection
    Set getChildren = nodeChildren
End Function
Public Function getPosition() As Variant
    getPosition = nodePosition
End Function
Public Function getType() As String
    getType = nodeType
End Function
Public Function getUid() As String
    getUid = nodeUid
End Function

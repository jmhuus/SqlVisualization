Attribute VB_Name = "TestMod"
Option Explicit



Public Sub testNodeTree()

    Dim rootPosition(1) As Variant
    rootPosition(0) = 10
    rootPosition(1) = 10
    
    
    Dim pg As Visio.Page
    Set pg = Visio.ActivePage
    
    
    ' Init nodes
    Dim n1 As New Node
    Dim n2 As New Node
    Dim n3 As New Node
    Dim n4 As New Node
    Dim n5 As New Node
    n1.constructor pg, "Table1", Array(10, 10), "Table"
    n2.constructor pg, "Table2", Array(5, 5), "Table"
    n3.constructor pg, "Table3", Array(15, 5), "Table"
    n4.constructor pg, "Table4", Array(10, 0), "Table"
    n5.constructor pg, "Table5", Array(20, 0), "Table"
    
    
    ' Add children to nodes
    n1.addChild n2
    n1.addChild n3
    n3.addChild n4
    n3.addChild n5
    
    
    n1.connectChildren
    
    
    
    
End Sub

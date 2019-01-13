Attribute VB_Name = "TestMod"
Option Explicit



Public Sub testNodeTree()

    Dim rootPosition(1) As Variant
    rootPosition(0) = 10
    rootPosition(1) = 10
    
    
    Dim pg As Visio.Page
    Set pg = Visio.ActivePage
    
    
    ' Init nodes
    Dim n0 As New Node
    Dim n1 As New Node
    Dim n2 As New Node
    Dim n3 As New Node
    Dim n4 As New Node
    Dim n5 As New Node
    n0.constructor pg, "Table1_MT", Array(10, 11), "Query"
    n1.constructor pg, "Table1", Array(10, 10), "Table"
    n2.constructor pg, "Table2", Array(5, 5), "Table"
    n3.constructor pg, "Table3", Array(15, 5), "Table"
    n4.constructor pg, "Table4", Array(10, 0), "Table"
    n5.constructor pg, "Table5", Array(20, 0), "Table"
    
    
    ' Add children to nodes
    n0.addChild n1
    n1.addChild n2
    n1.addChild n3
    n3.addChild n4
    n3.addChild n5
    
    
    ' Begin connecting children; recursion
    n0.connectChildren
    
    
    
    
End Sub

Attribute VB_Name = "TestMod"
Option Explicit



Public Sub testNodeTree()

    Dim rootPosition(1) As Variant
    rootPosition(0) = 10
    rootPosition(1) = 10
    
    
    Dim pg As Visio.Page
    Set pg = Visio.ActivePage
    
    
    ' Init nodes
    Dim n1_MT As New Node
    Dim n1_TBL As New Node
    Dim n2_MT As New Node
    Dim n2_TBL As New Node
    Dim n3_MT As New Node
    Dim n3_TBL As New Node
    Dim n4_MT As New Node
    Dim n4_TBL As New Node
    Dim n5_MT As New Node
    Dim n5_TBL As New Node
    n1_MT.constructor pg, "Table1_MT", Array(10, 10.5), "MakeTableQuery"
    n1_TBL.constructor pg, "Table1_TBL", Array(n1_MT.getPosition(1), n1_MT.getPosition(1) + 0.5), "Table"
    
    n2_MT.constructor pg, "Table2_MT", Array(5, 5.5), "MakeTableQuery"
    n2_TBL.constructor pg, "Table2_TBL", Array(5, 5), "Table"
    
    n3_MT.constructor pg, "Table3_MT", Array(15, 5.5), "MakeTableQuery"
    n3_TBL.constructor pg, "Table3_TBL", Array(15, 5), "Table"
    
    n4_MT.constructor pg, "Table4_MT", Array(10, 0.5), "MakeTableQuery"
    n4_TBL.constructor pg, "Table4_TBL", Array(10, 0), "Table"
    
    n5_MT.constructor pg, "Table5_MT", Array(20, 0.5), "MakeTableQuery"
    n5_TBL.constructor pg, "Table5_TBL", Array(20, 0), "Table"
    
    
    ' Add children to nodes
    n1_MT.addChild n1_TBL
    n1_TBL.addChild n2_MT
    n1_TBL.addChild n3_MT
    n2_MT.addChild n2_TBL
    n3_MT.addChild n3_TBL
    n3_TBL.addChild n4_MT
    n3_TBL.addChild n5_MT
    n4_MT.addChild n4_TBL
    n5_MT.addChild n5_TBL
    
    
    
    ' Begin connecting children; recursion
    n1_MT.connectChildren
    
    
    
    
End Sub

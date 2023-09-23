package com.peakmain.analytics.plugin.ext

import com.peakmain.analytics.plugin.entity.PeakmainMethodCell
import org.objectweb.asm.Opcodes

class PeakmainHookConfig {
    /**
     * android.gradle 3.2.1 版本中，针对 Lambda 表达式处理
     */
    public final static HashMap<String, PeakmainMethodCell> LAMBDA_METHODS = new HashMap<>()
    static {
        PeakmainMethodCell onClick = new PeakmainMethodCell(
                'onClick',
                '(Landroid/view/View;)V',
                'Landroid/view/View$OnClickListener;',
                'trackViewOnClickLambda',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD])
        LAMBDA_METHODS.put(onClick.parent + onClick.name + onClick.desc, onClick)
        PeakmainMethodCell onCheckedChanged = new PeakmainMethodCell(
                'onCheckedChanged',
                '(Landroid/widget/CompoundButton;Z)V',
                'Landroid/widget/CompoundButton$OnCheckedChangeListener;',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD])
        LAMBDA_METHODS.put(onCheckedChanged.parent + onCheckedChanged.name + onCheckedChanged.desc, onCheckedChanged)

        PeakmainMethodCell onRatingChanged = new PeakmainMethodCell(
                'onRatingChanged',
                '(Landroid/widget/RatingBar;FZ)V',
                'Landroid/widget/RatingBar$OnRatingBarChangeListener;',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD])
        LAMBDA_METHODS.put(onRatingChanged.parent + onRatingChanged.name + onRatingChanged.desc, onRatingChanged)


        PeakmainMethodCell onStopTrackingTouch = new PeakmainMethodCell(
                'onStopTrackingTouch',
                '(Landroid/widget/SeekBar;)V',
                'Landroid/widget/SeekBar$OnSeekBarChangeListener;',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD])
        LAMBDA_METHODS.put(onStopTrackingTouch.parent + onStopTrackingTouch.name + onStopTrackingTouch.desc, onStopTrackingTouch)

        PeakmainMethodCell onClick1 = new PeakmainMethodCell(
                'onClick',
                '(Landroid/content/DialogInterface;I)V',
                'Landroid/content/DialogInterface$OnClickListener;',
                'trackViewOnClick',
                '(Landroid/content/DialogInterface;I)V',
                1, 2,
                [Opcodes.ALOAD, Opcodes.ILOAD])
        LAMBDA_METHODS.put(onClick1.parent + onClick1.name + onClick1.desc, onClick1)

        PeakmainMethodCell onItemClick = new PeakmainMethodCell(
                'onItemClick',
                '(Landroid/widget/AdapterView;Landroid/view/View;IJ)V',
                'Landroid/widget/AdapterView$OnItemClickListener;',
                'trackViewOnClick',
                '(Landroid/widget/AdapterView;Landroid/view/View;I)V',
                1, 3,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD])
        LAMBDA_METHODS.put(onItemClick.parent + onItemClick.name + onItemClick.desc, onItemClick)

        PeakmainMethodCell onGroupClick = new PeakmainMethodCell(
                'onGroupClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;IJ)Z',
                'Landroid/widget/ExpandableListView$OnGroupClickListener;',
                'trackExpandableListViewGroupOnClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;I)V',
                1, 3,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD])
        LAMBDA_METHODS.put(onGroupClick.parent + onGroupClick.name + onGroupClick.desc, onGroupClick)

        PeakmainMethodCell onChildClick = new PeakmainMethodCell(
                'onChildClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;IIJ)Z',
                'Landroid/widget/ExpandableListView$OnChildClickListener;',
                'trackExpandableListViewChildOnClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;II)V',
                1, 4,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD, Opcodes.ILOAD])
        LAMBDA_METHODS.put(onChildClick.parent + onChildClick.name + onChildClick.desc, onChildClick)

        PeakmainMethodCell onTabChanged = new PeakmainMethodCell(
                'onTabChanged',
                '(Ljava/lang/String;)V',
                'Landroid/widget/TabHost$OnTabChangeListener;',
                'trackTabHost',
                '(Ljava/lang/String;)V',
                1, 1,
                [Opcodes.ALOAD])
        LAMBDA_METHODS.put(onTabChanged.parent + onTabChanged.name + onTabChanged.desc, onTabChanged)
    }
    
}
/*
Copyright (c) 2000 The Regents of the University of California.
All rights reserved.

Permission to use, copy, modify, and distribute this software for any
purpose, without fee, and without written agreement is hereby granted,
provided that the above copyright notice and the following two
paragraphs appear in all copies of this software.

IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR
DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING OUT
OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE UNIVERSITY OF
CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
AND FITNESS FOR A PARTICULAR PURPOSE.  THE SOFTWARE PROVIDED HEREUNDER IS
ON AN "AS IS" BASIS, AND THE UNIVERSITY OF CALIFORNIA HAS NO OBLIGATION TO
PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
*/

// This is a project skeleton file

import java.io.PrintStream;
import java.util.Vector;
import java.util.Enumeration;
import java.util.*;

/** This class is used for representing the inheritance tree during code
	generation. You will need to fill in some of its methods and
	potentially extend it in other useful ways. */
class CgenClassTable extends SymbolTable {

	// Global class table for cool-tree's code()
	public static CgenClassTable ct;

	/** All classes in the program, represented as CgenNode */
	private Vector nds;
	
	// mapping from class name to CgenNode
	private Map<AbstractSymbol, CgenNode> nodeMap;
	
	// global frame point offset (from SP)
	public int fpOffset;

	/** This is the stream to which assembly instructions are output */
	private PrintStream str;

	private int stringclasstag;
	private int intclasstag;
	private int boolclasstag;


	// The following methods emit code for constants and global
	// declarations.

	/** Emits code to start the .data segment and to
	 * declare the global names.
	 * */
	private void codeGlobalData() {
		// The following global names must be defined first.

		// set FP offset to be below SP by 1
		fpOffset = 0;

		str.println("\n# Global Data");

		str.print("\t.data\n" + CgenSupport.ALIGN);
		str.println(CgenSupport.GLOBAL + CgenSupport.CLASSNAMETAB);
		str.print(CgenSupport.GLOBAL); 
		CgenSupport.emitProtObjRef(TreeConstants.Main, str);
		str.println("");
		str.print(CgenSupport.GLOBAL); 
		CgenSupport.emitProtObjRef(TreeConstants.Int, str);
		str.println("");
		str.print(CgenSupport.GLOBAL); 
		CgenSupport.emitProtObjRef(TreeConstants.Str, str);
		str.println("");
		str.print(CgenSupport.GLOBAL); 
		BoolConst.falsebool.codeRef(str);
		str.println("");
		str.print(CgenSupport.GLOBAL); 
		BoolConst.truebool.codeRef(str);
		str.println("");
		str.println(CgenSupport.GLOBAL + CgenSupport.INTTAG);
		str.println(CgenSupport.GLOBAL + CgenSupport.BOOLTAG);
		str.println(CgenSupport.GLOBAL + CgenSupport.STRINGTAG);

		// We also need to know the tag of the Int, String, and Bool classes
		// during code generation.

		str.println(CgenSupport.INTTAG + CgenSupport.LABEL 
					+ CgenSupport.WORD + intclasstag);
		str.println(CgenSupport.BOOLTAG + CgenSupport.LABEL 
					+ CgenSupport.WORD + boolclasstag);
		str.println(CgenSupport.STRINGTAG + CgenSupport.LABEL 
					+ CgenSupport.WORD + stringclasstag);

	}

	/** Emits code to start the .text segment and to
	 * declare the global names.
	 * */
	private void codeGlobalText() {
		str.println("\n# Global Text");
	
		str.println(CgenSupport.GLOBAL + CgenSupport.HEAP_START);
		str.print(CgenSupport.HEAP_START + CgenSupport.LABEL);
		str.println(CgenSupport.WORD + 0);
		str.println("\t.text");
		str.print(CgenSupport.GLOBAL);
		CgenSupport.emitInitRef(TreeConstants.Main, str);
		str.println("");
		str.print(CgenSupport.GLOBAL);
		CgenSupport.emitInitRef(TreeConstants.Int, str);
		str.println("");
		str.print(CgenSupport.GLOBAL);
		CgenSupport.emitInitRef(TreeConstants.Str, str);
		str.println("");
		str.print(CgenSupport.GLOBAL);
		CgenSupport.emitInitRef(TreeConstants.Bool, str);
		str.println("");
		str.print(CgenSupport.GLOBAL);
		CgenSupport.emitMethodRef(TreeConstants.Main, TreeConstants.main_meth, str);
		str.println("");
	}

	/** Emits code definitions for boolean constants. */
	private void codeBools(int classtag) {
		BoolConst.falsebool.codeDef(classtag, str);
		BoolConst.truebool.codeDef(classtag, str);
	}

	/** Generates GC choice constants (pointers to GC functions) */
	private void codeSelectGc() {
		str.println(CgenSupport.GLOBAL + "_MemMgr_INITIALIZER");
		str.println("_MemMgr_INITIALIZER:");
		str.println(CgenSupport.WORD 
					+ CgenSupport.gcInitNames[Flags.cgen_Memmgr]);

		str.println(CgenSupport.GLOBAL + "_MemMgr_COLLECTOR");
		str.println("_MemMgr_COLLECTOR:");
		str.println(CgenSupport.WORD 
					+ CgenSupport.gcCollectNames[Flags.cgen_Memmgr]);

		str.println(CgenSupport.GLOBAL + "_MemMgr_TEST");
		str.println("_MemMgr_TEST:");
		str.println(CgenSupport.WORD 
					+ ((Flags.cgen_Memmgr_Test == Flags.GC_TEST) ? "1" : "0"));
	}

	/** Emits code to reserve space for and initialize all of the
	 * constants.  Class names should have been added to the string
	 * table (in the supplied code, is is done during the construction
	 * of the inheritance graph), and code for emitting string constants
	 * as a side effect adds the string's length to the integer table.
	 * The constants are emmitted by running through the stringtable and
	 * inttable and producing code for each entry. */
	private void codeConstants() {
		
		str.println("\n# Constants");
	
		// Add constants that are required by the code generator.
		AbstractTable.stringtable.addString("");
		AbstractTable.inttable.addString("0");

		AbstractTable.stringtable.codeStringTable(stringclasstag, str);
		AbstractTable.inttable.codeStringTable(intclasstag, str);
		codeBools(boolclasstag);
	}
	
	private void codeClassNameTable() {
		str.println("\n# Class Name Table");
		str.print(CgenSupport.CLASSNAMETAB + CgenSupport.LABEL);
		
		// code the name of each class
		for (Object obj : nds) {
			str.print(CgenSupport.WORD); 
			((CgenNode)obj).getNameStrSym().codeRef(str);
			str.println("\t# " + obj);
		}
	}
	
	private void codeClassObjTable() {
		str.println("\n# Class Object Table");
		str.print(CgenSupport.CLASSOBJTAB + CgenSupport.LABEL);
		for (Object obj : nds) {
			AbstractSymbol className = ((CgenNode)obj).getName();
			
			// code prototype object reference
			str.print(CgenSupport.WORD);
			CgenSupport.emitProtObjRef(className, str);
			str.println();
			
			// code initializer reference
			str.print(CgenSupport.WORD); 
			CgenSupport.emitInitRef(className, str);
			str.println();
		}
	}
	
	private void codeDispTables() {
		str.println("\n# Dispatch Tables");
		for (Object obj : nds) {
			CgenNode nd = (CgenNode)obj;

			// code class method reference
			CgenSupport.emitDispTableRef(nd.getName(), str);
			str.print(CgenSupport.LABEL);
			nd.codeDispTab(str);
			
		}
	}
	
	
	
	private void codeProtObjs() {
		str.println("\n# Prototype Objects");
		for (Object obj : nds) {
			CgenNode nd = (CgenNode)obj;

			// code class attributes
			str.println(CgenSupport.WORD + "-1");	// GC tag
			CgenSupport.emitProtObjRef(nd.getName(), str);
			str.print(CgenSupport.LABEL);
			nd.codeProtObj(str);
		}
	}
	
	private void codeObjInits() {
		str.println("\n# Object Initializers");
		for(Object obj : nds) {
			CgenNode nd = (CgenNode)obj;

			// code object initailzers (constructor method)
			CgenSupport.emitInitRef(nd.getName(), str);
			str.print(CgenSupport.LABEL);
			nd.codeObjInit(str);
		}
	}
	
	private void codeClassMethods() {
		str.println("\n# Class Methods");
		for(Object obj : nds) {
		
			// code each of the class method
			CgenNode nd = (CgenNode)obj;
			nd.codeClassMethod(str);
			
		}
	}
	




	/** Creates data structures representing basic Cool classes (Object,
	 * IO, Int, Bool, String).  Please note: as is this method does not
	 * do anything useful; you will need to edit it to make if do what
	 * you want.
	 * */
	private void installBasicClasses() {
		AbstractSymbol filename 
			= AbstractTable.stringtable.addString("<basic class>");
		
		// A few special class names are installed in the lookup table
		// but not the class list.  Thus, these classes exist, but are
		// not part of the inheritance hierarchy.  No_class serves as
		// the parent of Object and the other special classes.
		// SELF_TYPE is the self class; it cannot be redefined or
		// inherited.  prim_slot is a class known to the code generator.

		addId(TreeConstants.No_class,
			  new CgenNode(new class_(0,
									  TreeConstants.No_class,
									  TreeConstants.No_class,
									  new Features(0),
									  filename),
						   CgenNode.Basic, this));

		addId(TreeConstants.SELF_TYPE,
			  new CgenNode(new class_(0,
									  TreeConstants.SELF_TYPE,
									  TreeConstants.No_class,
									  new Features(0),
									  filename),
						   CgenNode.Basic, this));
		
		addId(TreeConstants.prim_slot,
			  new CgenNode(new class_(0,
									  TreeConstants.prim_slot,
									  TreeConstants.No_class,
									  new Features(0),
									  filename),
						   CgenNode.Basic, this));

		// The Object class has no parent class. Its methods are
		//		cool_abort() : Object	aborts the program
		//		type_name() : Str		returns a string representation 
		//								 of class name
		//		copy() : SELF_TYPE	   returns a copy of the object

		class_ Object_class = 
			new class_(0, 
					   TreeConstants.Object_, 
					   TreeConstants.No_class,
					   new Features(0)
						   .appendElement(new method(0, 
											  TreeConstants.cool_abort, 
											  new Formals(0), 
											  TreeConstants.Object_, 
											  new no_expr(0)))
						   .appendElement(new method(0,
											  TreeConstants.type_name,
											  new Formals(0),
											  TreeConstants.Str,
											  new no_expr(0)))
						   .appendElement(new method(0,
											  TreeConstants.copy,
											  new Formals(0),
											  TreeConstants.SELF_TYPE,
											  new no_expr(0))),
					   filename);

		installClass(new CgenNode(Object_class, CgenNode.Basic, this));
		
		// The IO class inherits from Object. Its methods are
		//		out_string(Str) : SELF_TYPE  writes a string to the output
		//		out_int(Int) : SELF_TYPE	  "	an int	"  "	 "
		//		in_string() : Str			reads a string from the input
		//		in_int() : Int				"   an int	 "  "	 "

		class_ IO_class = 
			new class_(0,
					   TreeConstants.IO,
					   TreeConstants.Object_,
					   new Features(0)
						   .appendElement(new method(0,
											  TreeConstants.out_string,
											  new Formals(0)
												  .appendElement(new formal(0,
																	 TreeConstants.arg,
																	 TreeConstants.Str)),
											  TreeConstants.SELF_TYPE,
											  new no_expr(0)))
						   .appendElement(new method(0,
											  TreeConstants.out_int,
											  new Formals(0)
												  .appendElement(new formal(0,
																	 TreeConstants.arg,
																	 TreeConstants.Int)),
											  TreeConstants.SELF_TYPE,
											  new no_expr(0)))
						   .appendElement(new method(0,
											  TreeConstants.in_string,
											  new Formals(0),
											  TreeConstants.Str,
											  new no_expr(0)))
						   .appendElement(new method(0,
											  TreeConstants.in_int,
											  new Formals(0),
											  TreeConstants.Int,
											  new no_expr(0))),
					   filename);

		installClass(new CgenNode(IO_class, CgenNode.Basic, this));

		// The Int class has no methods and only a single attribute, the
		// "val" for the integer.

		class_ Int_class = 
			new class_(0,
					   TreeConstants.Int,
					   TreeConstants.Object_,
					   new Features(0)
						   .appendElement(new attr(0,
											TreeConstants.val,
											TreeConstants.prim_slot,
											new no_expr(0))),
					   filename);

		installClass(new CgenNode(Int_class, CgenNode.Basic, this));

		// Bool also has only the "val" slot.
		class_ Bool_class = 
			new class_(0,
					   TreeConstants.Bool,
					   TreeConstants.Object_,
					   new Features(0)
						   .appendElement(new attr(0,
											TreeConstants.val,
											TreeConstants.prim_slot,
											new no_expr(0))),
					   filename);

		installClass(new CgenNode(Bool_class, CgenNode.Basic, this));

		// The class Str has a number of slots and operations:
		//	   val							  the length of the string
		//	   str_field						the string itself
		//	   length() : Int				   returns length of the string
		//	   concat(arg: Str) : Str		   performs string concatenation
		//	   substr(arg: Int, arg2: Int): Str substring selection

		class_ Str_class =
			new class_(0,
					   TreeConstants.Str,
					   TreeConstants.Object_,
					   new Features(0)
						   .appendElement(new attr(0,
											TreeConstants.val,
											TreeConstants.Int,
											new no_expr(0)))
						   .appendElement(new attr(0,
											TreeConstants.str_field,
											TreeConstants.prim_slot,
											new no_expr(0)))
						   .appendElement(new method(0,
											  TreeConstants.length,
											  new Formals(0),
											  TreeConstants.Int,
											  new no_expr(0)))
						   .appendElement(new method(0,
											  TreeConstants.concat,
											  new Formals(0)
												  .appendElement(new formal(0,
																	 TreeConstants.arg, 
																	 TreeConstants.Str)),
											  TreeConstants.Str,
											  new no_expr(0)))
						   .appendElement(new method(0,
											  TreeConstants.substr,
											  new Formals(0)
												  .appendElement(new formal(0,
																	 TreeConstants.arg,
																	 TreeConstants.Int))
												  .appendElement(new formal(0,
																	 TreeConstants.arg2,
																	 TreeConstants.Int)),
											  TreeConstants.Str,
											  new no_expr(0))),
					   filename);

		installClass(new CgenNode(Str_class, CgenNode.Basic, this));
	}
		
	// The following creates an inheritance graph from
	// a list of classes.  The graph is implemented as
	// a tree of `CgenNode', and class names are placed
	// in the base class symbol table.
	
	private int nextClassTag = 0;
	private void installClass(CgenNode nd) {
		AbstractSymbol name = nd.getName();
		if (probe(name) != null) return;
		nds.addElement(nd);
		nodeMap.put(nd.getName(), nd);	
		addId(name, nd);
	}

	private void installClasses(Classes cs) {
		for (Enumeration e = cs.getElements(); e.hasMoreElements(); ) {
			installClass(new CgenNode((Class_)e.nextElement(), 
									   CgenNode.NotBasic, this));
		}
	}

	private void buildInheritanceTree() {
		for (Enumeration e = nds.elements(); e.hasMoreElements(); ) {
			setRelations((CgenNode)e.nextElement());
		}
		
		if(Flags.cgen_debug) {
			for(Enumeration e = nds.elements(); e.hasMoreElements();)
				System.out.println(e.nextElement());
		}
	}
	
	

	private void setRelations(CgenNode nd) {
		CgenNode parent = (CgenNode)probe(nd.getParent());
		nd.setParentNd(parent);
		parent.addChild(nd);
	}

	/** Constructs a new class table and invokes the code generator */
	public CgenClassTable(Classes cls, PrintStream str) {
	
		ct = this;
	
		nds = new Vector();
		nodeMap = new LinkedHashMap<AbstractSymbol, CgenNode>();

		this.str = str;

		enterScope();
		if (Flags.cgen_debug) System.out.println("Building CgenClassTable");
		
		installBasicClasses();
		installClasses(cls);
		buildInheritanceTree();
		root().buildFeatures();	// extract attribute/method info for each class
		root().assignTags();	// set the tags for class (needed by case statement)
		Collections.sort(nds);	// need to sort the class in order of their tag
		
		stringclasstag = nodeMap.get(TreeConstants.Str).getTag();
		intclasstag = nodeMap.get(TreeConstants.Int).getTag();
		boolclasstag = nodeMap.get(TreeConstants.Bool).getTag();

		code();

		exitScope();
	}
	

	/** This method is the meat of the code generator.  It is to be
		filled in programming assignment 5 */
	public void code() {
		if (Flags.cgen_debug) System.out.println("coding global data");
		codeGlobalData();

		if (Flags.cgen_debug) System.out.println("choosing gc");
		codeSelectGc();

		if (Flags.cgen_debug) System.out.println("coding constants");
		codeConstants();
		
		
		//				 Add your code to emit
		//				   - prototype objects
		//				   - class_nameTab
		//				   - dispatch tables

		if (Flags.cgen_debug) System.out.println("coding class name table");
		codeClassNameTable();
		
		if (Flags.cgen_debug) System.out.println("coding class object table");
		codeClassObjTable();

		if (Flags.cgen_debug) System.out.println("coding dispatch tables");
		codeDispTables();		

		if (Flags.cgen_debug) System.out.println("coding prototype objects");
		codeProtObjs();		
		



		if (Flags.cgen_debug) System.out.println("coding global text");
		codeGlobalText();

		//				 Add your code to emit
		//				   - object initializer
		//				   - the class methods
		//				   - etc...
		
		if (Flags.cgen_debug) System.out.println("coding object initializer");
		codeObjInits();
		
		if (Flags.cgen_debug) System.out.println("coding class methods");
		codeClassMethods();
	}

	/** Gets the root of the inheritance tree */
	public CgenNode root() {
		return (CgenNode)probe(TreeConstants.Object_);
	}
	
	/* given a class and a method, return the offset to the class's dispatch table */
	public int getMethodOffset(AbstractSymbol clazz, AbstractSymbol method) {
		CgenNode nd = nodeMap.get(clazz);
		
		if (Flags.cgen_debug) 
			System.out.printf("getMethodOffset %s %s\n", clazz, method);
		
		if(!nd.methOffsets.containsKey(method))
			return getMethodOffset(nd.getParent(), method);
		else
			return nd.methOffsets.get(method);
	}
	
	/* given a class and an attribute, return the offset to the object reference */
	public int getAttrOffset(AbstractSymbol clazz, AbstractSymbol a) {
		CgenNode nd = nodeMap.get(clazz);
		if(!nd.attrOffsets.containsKey(a))
			return getAttrOffset(nd.getParent(), a);
		else
			return nd.attrOffsets.get(a);
	}
	
	/* get the depth of the class in the inheritance tree */
	public int getDepth(AbstractSymbol clazz) {
		int depth = 0;
		CgenNode nd = nodeMap.get(clazz);
		while(nd.name != TreeConstants.Object_) {
			nd = nd.getParentNode();
			depth++;
		}
		return depth;
	}
	
	/* get the tag of a class */
	public int getMinTag(AbstractSymbol clazz) {
		return nodeMap.get(clazz).getTag();
	}
	
	/* get the maximum tag among this class's children */
	public int getMaxTag(AbstractSymbol clazz) {
		return nodeMap.get(clazz).getMaxChildTag();
	}
	
	/* get the current file name, given a class ;
		NOTE: now since there is only one class, return where Main is */
	public String getFilename(AbstractSymbol clazz) {
		return nodeMap.get(TreeConstants.Main).filename.toString();
	}
	
}
						  
	

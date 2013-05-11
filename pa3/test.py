#!/usr/bin/python

import os, sys, re

print '\n>>> CS143 PA3 Testing Script <<<'

print 'Building semant...'
os.system('gmake semant')

debug=''
for arg in sys.argv[1:]:

	if arg == '-s':
		debug = arg
		continue

	print 'Input file: ' + arg
	
	print 'Remove previous output files...'
	os.system('rm -f semant.out semant.err mysemant.out mysemant.err')

	# run our implementation and the reference 
	print 'Running reference...' 
	os.system('/usr/class/cs143/bin/.coolref/lexer ' + arg + 
		' | /usr/class/cs143/bin/.coolref/parser'
		' | /usr/class/cs143/bin/.coolref/semant > semant.out 2> semant.err')
	
	print 'Running our implementation...'
	#os.system('../mysemant ' + arg + ' > mysemant.out 2> mysemant.err')
	os.system('./lexer ' + arg +
		' | ./parser '
		' | ./semant %s > mysemant.out 2> mysemant.err' % debug)
	

	

	# compare outputs
	print 'Compare output...'
	if os.system('diff semant.out mysemant.out') == 0:
		print 'PASS'
	else:
		print 'FAIL'
	
	print 'Compare error...'
	if os.system('diff semant.err mysemant.err') == 0:
		print 'PASS'
	else:
		print 'FAIL'
	


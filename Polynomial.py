import math
import cmath
def factorial(n):
    fact = 1
    for i in range(n):
        fact *= (n-i)
    return fact

'''
This is a polymial class represented by storing the co-oefficents of the polynomial. In order to increase the efficienty of some of the operations - multiplicatoin and others that depend on multiplication,
the class contains an implementation of the Fast-fourier transform and Reverse fast fourier transform to switch back and forth between the coefficient representation of the polynomial and the sample representation
of the polynomial.

'''
class Polynomial:
    def __init__(self, coefList):
        self.poly = coefList
    def __str__(self):
        to_return =  "%.2f"%(self.poly[0])
        for i in range(1,len(self.poly)):
            if self.poly[i] == 0:
                continue
            current = self.poly[i]
            to_return += " + %.2fx^%d"%(current,i)
        return to_return

    # evaluates the polynomial at x
    def evaluate(self, x):
        result = 0
        for i in range(len(self.poly)):
            result += self.poly[i]*math.pow(x,i)
        return result
    def degree(self):
        coeficients = self.poly[:]
        coeficients.reverse()
        for item in coeficients[:]:
            if (item != 0):
                break
            coeficients.remove(item)
        if len(coeficients) > 0:
            to_return = len(coeficients) - 1
        else:
            to_return = len(coeficients)
        return to_return
    def __eq__(self, apoly):
        poly1 = self.poly[:]
        poly2 = apoly.poly[:]
        poly1.reverse()
        poly2.reverse()
        for item in poly1[:]:
            if item != 0:
                break
            poly1.remove(item)
        for value in poly2[:]:
            if value != 0:
                break
            poly2.remove(value)
        poly1.reverse()
        poly2.reverse()
        if (len(poly1) != len(poly2)):
            return False
        for i in range(len(poly1)):
            if poly1[i] != poly2[i]:
                return False
        return True
    def derivative(self):
        poly1 = self.poly[:]
        derivate = []
        if len(self.poly) == 1:
            return Polynomial([0])
        for i in range(len(self.poly)):
            derivate.append(i*self.poly[i])
        derivate.pop(0)
        return Polynomial(derivate)
    
    def nDerivative(self,n):
        poly1 = self.poly[:]
        poly2 = poly1[:]
        for i in range(len(poly2)):
            if (i-n) < 0:
                poly1.remove(poly2[i])
        for j in range(len(poly1)):
            if (poly1[j] != 0):
                poly1[j] = poly1[j]*(factorial(j+n)/factorial(j))
        if len(poly1)==0:
            poly1.append(0)
        return Polynomial(poly1)

    def getCoefficients(self):
        return self.poly

        
    def integral(self):
        poly1 = self.poly[:]
        integrate = []
        for i in range(len(poly1)):
            value = poly1[i]/(i+1.0)
            integrate.append(value)
        integrate.reverse()
        integrate.append(0)
        integrate.reverse()
        return Polynomial(integrate)
    
    def taylorOfPolynomial(self):
        poly1 = self.poly[:]
        newpoly = [Polynomial(poly1).evaluateAt(0)]
        for i in range(1,len(poly1)):
            newpoly.append(Polynomial(poly1).nDerivative(i).evaluateAt(0)/factorial(i))
        return Polynomial(newpoly)
    def composition(self, poly2):
        poly1 = self.poly[:]
        step1 = []
        step1.append(Polynomial([1].poly))
        step1.append(poly2)
        product = poly2
        for i in range(2,len(poly1)):
            product*=poly2
            step1.append(product)
        step2 = []
        for j in range(len(poly1)):
            for k in range(len(step2[j])):
                step1[j][k]=step1[j][k]*poly1[j]
            step2.append(step1[j])
        compose = sum(step2)
        return compose
    def __add__(self, bpoly):
        poly1 = []
        if(len(self.poly) < len(bpoly.poly)):
            for i in range(len(self.poly)):
                poly1.append(self.poly[i] + bpoly.poly[i])
            for j in range(len(self.poly),len(bpoly.poly)):
                poly1.append(bpoly.poly[j])
        elif (len(bpoly.poly) < len(self.poly)):
            for i in range(len(bpoly.poly)):
                poly1.append(bpoly.poly[i] + self.poly[i])
            for j in range(len(bpoly.poly),len(self.poly)):
                poly1.append(self.poly[j])
        else:
            for i in range(len(bpoly.poly)):
                poly1.append(bpoly.poly[i] + self.poly[i])
        return Polynomial(poly1)
    def __sub__(self, bpoly):
        poly1 = []
        if(len(self.poly) < len(bpoly.poly)):
            for i in range(len(self.poly)):
                poly1.append(self.poly[i] - bpoly.poly[i])
            for j in range(len(self.poly),len(bpoly.poly)):
                poly1.append(0-bpoly.poly[j])
        elif (len(bpoly.poly) < len(self.poly)):
            for i in range(len(bpoly.poly)):
                poly1.append(self.poly[i] - bpoly.poly[i])
            for j in range(len(bpoly.poly),len(self.poly)):
                poly1.append(self.poly[j])
        else:
            for i in range(len(bpoly.poly)):
                poly1.append(self.poly[i] - bpoly.poly[i])
        return Polynomial(poly1)

    def fastFourierTransform(self,x_choice):
        return fftHelper(self.poly,x_choice)

    def _fftHelper(coefficients,x_choice):
        if(len(coefficients) == 1):
            result = Polynomial(coefficients).evaluate(x_choice)
            mapping = dict()
            mapping[x_choice] = result
            return mapping
        else:
            evenCoeffs = collectCoefficients(coefficients,0)
            oddCoeffs = collectCoefficients(coefficients,1)

            evenMapping = _fftHelper(evenCoeffs,x_choice)
            oddMapping = _fftHelper(oddCoeffs,x_choice)

            mergedMapping = fftMerge(evenMapping,oddMapping, evenCoeffs,oddCoeffs)
            return mergedMapping

    def fftMerge(evenMapping,oddMapping,evenCoeffs,oddCoeffs):
        mergedMapping = {}
        for x in evenMapping.keys():
            x1 = cmath.sqrt(x);
            x2 = 0 - x1
            f_x1 = 0;
            f_x2 = 0;
            if(x not in evenMapping.keys()):
                f_x1 = Polynomial(evenCoeffs).evaluate(x) + x1*oddMapping[x]
                f_x2 = Polynomial(evenCoeffs).evaluate(x) + x2*oddMapping[x]
            elif(x not in oddMapping.keys()):
                f_x1 = evenMapping[x] + x1*Polynomial(oddCoeffs).evaluate(x)
                f_x2 = evenMapping[x] + x2*Polynomial(oddCoeffs).evaluate(x)
            else:
                f_x1 = evenMapping[x] + x1*oddMapping[x]
                f_x2 = evenMapping[x] + x2*oddMapping[x]

            if(x1 not in mergedMapping.keys()):
                mergedMapping[x1] = f_x1
            if(x2 not in mergedMapping.keys()):
                mergedMapping[x2] = f_x2

        for x in oddMapping.keys():
            x1 = cmath.sqrt(x);
            x2 = 0 - x1
            if(x1  in mergedMapping.keys() || x2 in mergedMapping.keys()):
                continue
                
            f_x1 = 0;
            f_x2 = 0;
            if(x not in evenMapping.keys()):
                f_x1 = Polynomial(evenCoeffs).evaluate(x) + x1*oddMapping[x]
                f_x2 = Polynomial(evenCoeffs).evaluate(x) + x2*oddMapping[x]
            elif(x not in oddMapping.keys()):
                f_x1 = evenMapping[x] + x1*Polynomial(oddCoeffs).evaluate(x)
                f_x2 = evenMapping[x] + x2*Polynomial(oddCoeffs).evaluate(x)
            else:
                f_x1 = evenMapping[x] + x1*oddMapping[x]
                f_x2 = evenMapping[x] + x2*oddMapping[x]

            mergedMapping[x1] = f_x1
            mergedMapping[x2] = f_x2

        return mergedMapping
    def reverseFFT(self,sampleForm):
        ''' to be implemented '''
            









    def collectCoefficients(coefficients,starting_index):

        newCoefficients = []
        for i in range(starting_index,len(coefficients),2):
            newCoefficients.append(coefficients[i])
        return newCoefficients




    def __mul__(self, bpoly):
        sample_form_bpoly = bpoly.fastFourierTransform(1)
        sample_form_self = self.poly.fastFourierTransform(1)

        product_in_sample_form = {}
        for x in sample_form_self.keys():
            product_in_sample_form[x] = sample_form_self[x]*sample_form_bpoly[x]

        product_in_coefficient_form = reverseFFT(product_in_sample_form)
        return Polynomial(product_in_coefficient_form)




    #implement the remaining methods
        
def main():
    help(str)
    p0 = Polynomial([0, 3, 4, 5, 0, 8])
    p1 = Polynomial([25, 0, 1, 0, 0, 0])
    p2 = Polynomial([-25, 1])
    p3 = Polynomial([6, 5, 1])
    p4 = Polynomial([0, 3, 4, 5, 0, 8])
    p5 = Polynomial([1])
    p6 = Polynomial([7, 5])
    p7 = Polynomial([3, 8, 12])
    pe = Polynomial([1/math.factorial(k) for k in range(10)])
    myPolys = [p0, p1, p2, p3, p4, p5, p6, p7, pe]
    for k in range(len(myPolys)):
        if k < 8:
            print("p"+str(k)+":")
        else:
            print("pe:")
        p = myPolys[k]
        print("    p=", p)
        print("    degree of p = ", p.degree())
        print("    p'=", p.derivative())
        print("    degree of p' = ", p.derivative().degree())
        print("    p(1)=", p.evaluate(1))
        print("    p'(1)=", p.derivative().evaluate(1))
        print("    p.integral()", p.integral())
        print("    degree of p.integral() = ", p.integral().degree())
        print("    p==p.derivative()", p==p.derivative())
        print("    p==p.integral()", p==p.integral())
        print()

    print("Addition")
    for k in range(len(myPolys)):
        if k < 8:
            print("p"+str(k)+":")
        else:
            print("pe:")
        p = myPolys[k]
        q = myPolys[(k+1)%8]
        print("    p = ", p)
        print("    q = ", q)
        print("    p+q = ", p+q)
        print("    q+p = ", q+p)
        print("    p+p = ", p+p)
        print()

    print("Subtraction")
    for k in range(len(myPolys)):
        if k < 8:
            print("p"+str(k)+":")
        else:
            print("pe:")
        p = myPolys[k]
        q = myPolys[(k+1)%8]
        print("    p = ", p)
        print("    q = ", q)
        print("    p-q = ", p-q)
        print("    q-p = ", q-p)
        print("    p-p = ", p-p)
        print()    

    print("Multiplication")
    for k in range(len(myPolys)):
        if k < 8:
            print("p"+str(k)+":")
        else:
            print("pe:")
        p = myPolys[k]
        q = myPolys[(k+1)%8]
        print("    p = ", p)
        print("    q = ", q)
        print("    p*q = ", p*q)
        print("    q*p = ", q*p)
        print("    p*p = ", p*p)
        print()
    print("Higher derivative: ")
    for k in myPolys:
        print("Original Function: ",k)
        print()
        print("Second derivative:\n",k.nDerivative(2))
        print()
        print("Third derivative:\n",k.nDerivative(3))

    for k in myPolys:
        print("Funtion evaluated at 8: ",k,k.evaluateAt(8))
        print()
        print("Function evaluated at 0",k,k.evaluateAt(0))
        print()
        print("Funcation evaluated at -3",k,k.evaluateAt(-3))

    for k in myPolys:
        print("Mclauren of polynomial %s: "%k,k.taylorOfPolynomial())
        print()
       
        
main()
